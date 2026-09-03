package com.bookflow.auth.controller;

import com.bookflow.auth.dto.request.LoginRequest;
import com.bookflow.auth.dto.request.RegisterRequest;
import com.bookflow.auth.dto.request.ResendVerificationRequest;
import com.bookflow.auth.dto.request.VerifyEmailRequest;
import com.bookflow.auth.dto.response.LoginResponse;
import com.bookflow.auth.service.concrete.AuthServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceHandler authServiceHandler;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody RegisterRequest request) {
        authServiceHandler.registerUser(request);
    }

    @PostMapping("/verify-email")
    @ResponseStatus(HttpStatus.OK)
    public void verifyEmail(@RequestBody VerifyEmailRequest verifyEmailRequest) {
        authServiceHandler.verifyEmail(verifyEmailRequest);
    }

    @PostMapping("/resend-verify")
    @ResponseStatus(HttpStatus.OK)
    public void resendVerificationRequest(@RequestBody ResendVerificationRequest verificationRequest) {
        authServiceHandler.resendVerificationRequest(verificationRequest);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
       return authServiceHandler.login(loginRequest);
    }
}
