package com.bookflow.email.abstraction;

import org.springframework.stereotype.Component;

@Component
public interface EmailService {
    void sendVerificationCode(String toEmail, String code);
}
