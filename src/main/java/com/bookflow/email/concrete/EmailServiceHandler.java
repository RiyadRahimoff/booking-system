package com.bookflow.email.concrete;

import com.bookflow.email.abstraction.EmailService;
import com.bookflow.exception.EmailSendException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailServiceHandler implements EmailService {
    private final JavaMailSender javaMailSender;


    @Override
    public void sendVerificationCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("YerimVar — Email Verification Code");

        message.setText(
                "Hello,\n\n" +
                        "Thank you for joining YerimVar!\n\n" +
                        "To complete your registration, please use the verification code below:\n\n" +
                        "Verification Code: " + code + "\n\n" +
                        "This code is valid for 5 minutes. For your security, please do not share it with anyone.\n\n" +
                        "If you did not request this code, you can safely ignore this email.\n\n" +
                        "Best regards,\n" +
                        "YerimVar Team"
        );

        try {
            javaMailSender.send(message);
        } catch (Exception ex) {
            throw new EmailSendException(
                    "Failed to send verification email! " + ex.getMessage()
            );
        }
    }

    @Override
    public void sendBlockUserMessage(String toEmail, String reason) {
        SimpleMailMessage message1 = new SimpleMailMessage();

        message1.setTo(toEmail);
        message1.setSubject("BookFlow — Your Account Has Been Blocked");

        message1.setText(
                "Hello,\n\n" +
                        "We regret to inform you that your YerimVar account has been blocked.\n\n" +
                        "Reason: " + reason + "\n\n" +
                        "During this period, you will not be able to access your account or use our platform services.\n\n" +
                        "If you believe this action was taken by mistake, please contact our support team.\n\n" +
                        "We take account security and platform policies seriously.\n\n" +
                        "Best regards,\n" +
                        "YerimVar Team"
        );

        try {
            javaMailSender.send(message1);
        } catch (Exception ex) {
            throw new EmailSendException("Failed to send account blocked email! " + ex.getMessage());
        }
    }

    @Override
    public void sendUnblockUserMessage(String toEmail) {
        SimpleMailMessage message1 = new SimpleMailMessage();

        message1.setTo(toEmail);
        message1.setSubject("YerimVar — Your Account Has Been Unblocked");

        message1.setText(
                "Hello,\n\n" +
                        "We are pleased to inform you that your YerimVar account has been successfully unblocked.\n\n" +
                        "You can now access your account and use all available platform services again.\n\n" +
                        "Thank you for your patience and understanding.\n\n" +
                        "If you have any questions or experience any issues, please contact our support team.\n\n" +
                        "We appreciate having you as part of YerimVar.\n\n" +
                        "Best regards,\n" +
                        "YerimVar Team"
        );

        try {
            javaMailSender.send(message1);
        } catch (Exception ex) {
            throw new EmailSendException("Failed to send account blocked email! " + ex.getMessage());
        }
    }
}
