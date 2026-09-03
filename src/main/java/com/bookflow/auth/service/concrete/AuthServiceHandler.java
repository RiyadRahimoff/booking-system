package com.bookflow.auth.service.concrete;

import com.bookflow.auth.dto.request.*;
import com.bookflow.auth.dto.response.LoginResponse;
import com.bookflow.auth.security.JwtService;
import com.bookflow.auth.service.abstraction.AuthService;
import com.bookflow.email.abstraction.EmailProducer;
import com.bookflow.email.abstraction.EmailService;
import com.bookflow.email.entity.EmailVerificationMessage;
import com.bookflow.exception.*;
import com.bookflow.user.entity.UserEntity;
import com.bookflow.user.enums.StatusEnum;
import com.bookflow.user.enums.UserEnum;
import com.bookflow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceHandler implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final StringRedisTemplate redisTemplate;
    private final EmailProducer emailProducer;

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Duration VERIFICATION_TTL = Duration.ofMinutes(5);
    private static final Duration RESEND_COOLDOWN = Duration.ofSeconds(60);
    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(7);

    @Override
    public void registerUser(RegisterRequest request) {

        UserEnum role = parseRole(request.role());

        Optional<UserEntity> existingUser =
                userRepository.findByEmail(request.email());

        if (existingUser.isPresent()) {

            UserEntity user = existingUser.get();

            if (user.getStatus() == StatusEnum.PENDING) {
                throw new EmailAlreadyVerifiedException(
                        "Email is already registered but not verified"
                );
            }

            throw new EmailAlreadyExistsException(
                    "Email is already registered: " + request.email()
            );
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        UserEntity user = UserEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(encodedPassword)
                .role(role)
                .status(StatusEnum.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        String code = generateVerificationCode();

        saveVerificationCode(request.email(), code);

        emailProducer.sendVerificationEmailMessage(new EmailVerificationMessage(request.email(),code));

    }

    @Override
    public void verifyEmail(VerifyEmailRequest verifyEmailRequest) {
        UserEntity user = userRepository.findByEmail(verifyEmailRequest.email())
                .orElseThrow(() -> new UserNotFoundException("User not found!? " + verifyEmailRequest.email()));

        if (user.getStatus() == StatusEnum.ACTIVE) {
            throw new UserAlreadyExistException("This user already registered!");
        }

        String key = verificationKey(verifyEmailRequest.email());
        String storedCode = redisTemplate.opsForValue().get(key);

        if (storedCode == null) {
            throw new VerificationCodeExpiredException(
                    "Verification code has expired or does not exist");
        }

        if (!storedCode.equals(verifyEmailRequest.code())) {
            throw new InvalidVerificationCodeException("Verification code is invalid");
        }

        user.setStatus(StatusEnum.ACTIVE);
        userRepository.save(user);
        redisTemplate.delete(key);

    }

    @Override
    public void resendVerificationRequest(ResendVerificationRequest verificationRequest) {
       UserEntity user = userRepository.findByEmail(verificationRequest.email())
               .orElseThrow(()->new UserNotFoundException("User not found?!"));

       if(user.getStatus()== StatusEnum.ACTIVE){
           throw new UserAlreadyExistException("User account already active");
       }
        String cooldownKey = "resend:" + verificationRequest.email();

        if (Boolean.TRUE.equals(redisTemplate.hasKey(cooldownKey))) {
            throw new ResendCooldownException(
                    "Please wait before requesting a new code"
            );
        }

        String code = generateVerificationCode();
        saveVerificationCode(verificationRequest.email(),code);

        emailProducer.sendVerificationEmailMessage(new EmailVerificationMessage(verificationRequest.email(),code));

        redisTemplate.opsForValue().set(
                cooldownKey,
                "1",
                RESEND_COOLDOWN
        );
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(()->new InvalidCredentialsException("Invalid email or password"));

        if(!passwordEncoder.matches(loginRequest.password(),user.getPassword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }
        if(user.getStatus()==StatusEnum.PENDING){
            throw new UserStatusPendingException("User account need to be verify.");

        }
        if(user.getStatus()==StatusEnum.BLOCKED){
            throw new UserStatusPendingException("User account blocked. Please contact admin");

        }
        if(user.getStatus()==StatusEnum.INACTIVE){
            throw new UserStatusPendingException("User account inactive.");
        }

        String accessToken = jwtService.generateAccessToken(loginRequest.email());
        String refreshToken = jwtService.generateRefreshToken(loginRequest.email());

        saveRefreshToken(user.getId(),refreshToken);

        return LoginResponse.of(accessToken,refreshToken);

    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return null;
    }

    @Override
    public void logout(String refreshToken) {

    }

    private UserEnum parseRole(String rawRole) {
        try {
            UserEnum role = UserEnum.valueOf(rawRole.toUpperCase());
            if (role != UserEnum.CUSTOMER && role != UserEnum.OWNER) {
                throw new InvalidRoleException("Role must be CUSTOMER or OWNER");
            }
            return role;
        } catch (IllegalArgumentException e) {
            throw new InvalidRoleException("Invalid role: " + rawRole);
        }

    }

    private String generateVerificationCode() {
        int code = RANDOM.nextInt(1_000_000);
        return String.format("%06d", code);
    }

    private void saveVerificationCode(String email, String code) {
        redisTemplate.opsForValue().set(verificationKey(email), code, VERIFICATION_TTL);
    }

    private String verificationKey(String email) {
        return "verification:" + email;
    }
    private void saveRefreshToken(Long userId, String refreshToken) {
        String hashedToken = hashToken(refreshToken);
        String key = refreshTokenKey(userId);
        redisTemplate.opsForValue().set(key, hashedToken, REFRESH_TOKEN_TTL);
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }

    private String refreshTokenKey(Long userId) {
        return "refresh:" + userId;
    }
}
