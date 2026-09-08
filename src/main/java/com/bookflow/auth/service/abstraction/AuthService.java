package com.bookflow.auth.service.abstraction;

import com.bookflow.auth.dto.request.*;
import com.bookflow.auth.dto.response.LoginResponse;
import com.bookflow.auth.dto.request.ForgotPasswordRequest;

public interface AuthService {
    void registerUser(RegisterRequest request);

    void verifyEmail(VerifyEmailRequest verifyEmailRequest);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

    void resendVerificationRequest(ResendVerificationRequest verificationRequest);

    LoginResponse login(LoginRequest loginRequest);

    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    void logout(String refreshToken);


}
