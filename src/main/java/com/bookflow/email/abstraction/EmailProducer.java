package com.bookflow.email.abstraction;

import com.bookflow.email.entity.EmailBlockMessage;
import com.bookflow.email.entity.EmailUnblockMessage;
import com.bookflow.email.entity.EmailVerificationMessage;

public interface EmailProducer {
    void sendVerificationEmailMessage(EmailVerificationMessage verificationMessage);
    void sendBlockUserEmailMessage(EmailBlockMessage emailBlockMessage);
    void sendUnbLockEmailMessage(EmailUnblockMessage emailUnblockMessage);
}
