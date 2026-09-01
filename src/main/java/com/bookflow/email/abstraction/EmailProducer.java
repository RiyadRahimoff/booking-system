package com.bookflow.email.abstraction;

import com.bookflow.email.entity.EmailVerificationMessage;

public interface EmailProducer {
    void sendVerificationEmailMessage(EmailVerificationMessage verificationMessage);
}
