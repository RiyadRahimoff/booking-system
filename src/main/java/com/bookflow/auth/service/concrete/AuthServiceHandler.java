package com.bookflow.auth.service.concrete;

import com.bookflow.auth.dto.request.*;
import com.bookflow.auth.dto.response.LoginResponse;
import com.bookflow.auth.service.abstraction.AuthService;
import com.bookflow.email.EmailService;
import com.bookflow.exception.*;
import com.bookflow.user.entity.UserEntity;
import com.bookflow.user.enums.StatusEnum;
import com.bookflow.user.enums.UserEnum;
import com.bookflow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceHandler implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final StringRedisTemplate redisTemplate;

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Duration VERIFICATION_TTL = Duration.ofMinutes(5);
    private static final Duration RESEND_COOLDOWN = Duration.ofSeconds(60);


    @Override
    public void registerUser(RegisterRequest request) {
        UserEnum role = parseRole(request.role());
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email is already registered: " + request.email());
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
        emailService.sendVerificationCode(request.email(), code);


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

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
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
}
