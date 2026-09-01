package com.bookflow.email.concrete;

import com.bookflow.config.RabbitMQConfig;
import com.bookflow.email.abstraction.EmailService;
import com.bookflow.email.entity.EmailVerificationMessage;
import com.bookflow.exception.EmailSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_VERIFICATION_QUEUE)
    public void consumeVerificationEmail(EmailVerificationMessage message) {
        log.info("Consumed verification email message for {}", message.email());

        try {
            emailService.sendVerificationCode(message.email(), message.code());
        } catch (Exception e) {
            log.error("Failed to process verification email for {}", message.email(), e);
            throw new EmailSendException("Email system crashed!");
        }
    }
}