package com.bookflow.email.concrete;

import com.bookflow.config.RabbitMQConfig;
import com.bookflow.email.abstraction.EmailProducer;
import com.bookflow.email.entity.EmailVerificationMessage;
import com.bookflow.exception.RabbitMQException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailProducerHandler implements EmailProducer {
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void sendVerificationEmailMessage(EmailVerificationMessage verificationMessage) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_EXCHANGE,
                    RabbitMQConfig.EMAIL_VERIFICATION_ROUTING_KEY,
                    verificationMessage);
            log.info("Verification email message queued for {}", verificationMessage.email());
        } catch (AmqpException e){
            log.error("Failed to publish verification email message for {}", verificationMessage.email(), e);
            throw new RabbitMQException("RabbitMQ crashed!");
        }

    }
}
