package com.bookflow.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EMAIL_EXCHANGE = "email.exchange";

    public static final String EMAIL_VERIFICATION_QUEUE = "email.verification.queue";
    public static final String EMAIL_VERIFICATION_ROUTING_KEY = "email.verification";

    public static final String EMAIL_BLOCK_QUEUE = "email.block.queue";
    public static final String EMAIL_BLOCK_ROUTING_KEY = "email.block";

    public static final String EMAIL_UNBLOCK_QUEUE = "email.unblock.queue";
    public static final String EMAIL_UNBLOCK_ROUTING_KEY = "email.unblock";


    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(EMAIL_EXCHANGE);
    }


    @Bean
    public Queue emailVerificationQueue() {
        return new Queue(EMAIL_VERIFICATION_QUEUE, true);
    }

    @Bean
    public Binding emailVerificationBinding(
            Queue emailVerificationQueue,
            DirectExchange emailExchange) {

        return BindingBuilder
                .bind(emailVerificationQueue)
                .to(emailExchange)
                .with(EMAIL_VERIFICATION_ROUTING_KEY);
    }


    @Bean
    public Queue emailBlockQueue() {
        return new Queue(EMAIL_BLOCK_QUEUE, true);
    }

    @Bean
    public Binding emailBlockBinding(
            Queue emailBlockQueue,
            DirectExchange emailExchange) {

        return BindingBuilder
                .bind(emailBlockQueue)
                .to(emailExchange)
                .with(EMAIL_BLOCK_ROUTING_KEY);
    }


    @Bean
    public Queue emailUnblockQueue() {
        return new Queue(EMAIL_UNBLOCK_QUEUE, true);
    }

    @Bean
    public Binding emailUnblockBinding(
            Queue emailUnblockQueue,
            DirectExchange emailExchange) {

        return BindingBuilder
                .bind(emailUnblockQueue)
                .to(emailExchange)
                .with(EMAIL_UNBLOCK_ROUTING_KEY);
    }



    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}