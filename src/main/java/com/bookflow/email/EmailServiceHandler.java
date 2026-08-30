package com.bookflow.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceHandler implements EmailService {
    @Override
    public void sendVerificationCode(String toEmail, String code) {
        log.info("Verification code for {} is {}", toEmail, code);
    }
}
