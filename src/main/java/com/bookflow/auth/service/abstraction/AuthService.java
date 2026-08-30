package com.bookflow.auth.service.abstraction;

import com.bookflow.auth.dto.request.*;
import com.bookflow.auth.dto.response.LoginResponse;

public interface AuthService {
    void registerUser(RegisterRequest request);

    void verifyEmail(VerifyEmailRequest verifyEmailRequest);

    void resendVerificationRequest(ResendVerificationRequest verificationRequest);

    LoginResponse login(LoginRequest loginRequest);

    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    void logout(String refreshToken);


}
