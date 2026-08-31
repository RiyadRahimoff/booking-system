package com.bookflow.email;

import com.bookflow.exception.EmailSendException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
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
        message.setSubject("BookFlow — Email Verification Code");
        message.setText("Your verification code is: " + code +
                "\n\nThis code will expire in 5 minutes.");

        try{
         javaMailSender.send(message);
        }catch (Exception ex){
            throw new EmailSendException("Email system crashed!");
        }
    }
}
