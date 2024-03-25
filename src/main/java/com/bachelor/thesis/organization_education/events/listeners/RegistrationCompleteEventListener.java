package com.bachelor.thesis.organization_education.events.listeners;

import com.bachelor.thesis.organization_education.events.RegistrationCompleteEvent;
import com.bachelor.thesis.organization_education.exceptions.MailException;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private static final String MAIL_MESSAGE = """
            <p> Hi, %s, </p>
            <p>Thank you for registering with us! Please, follow the link below to complete your registration.</p>
            <a href="%s">Verify your email to activate your account</a>
            <p> Thank you <br> Users University Classroom
            """;

    private final JavaMailSender mailSender;
    private final Dotenv dotenv;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        try {
            var username = event.getRegisteredResponse().getUsername();
            var url = event.getApplicationUrl() + "/verifyEmail?token=" + event.getRegisteredResponse().getAccessToken();
            sendVerificationEmail(url, username);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new MailException(e.getMessage());
        }
    }

    private void sendVerificationEmail(String url, String username) throws MessagingException, UnsupportedEncodingException {
        var subject = "Email Verification";
        var senderName = "User University Classroom";
        var mailContent = String.format(MAIL_MESSAGE, username, url);
        var message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        var workEmail = dotenv.get("MAIL_USERNAME");

        messageHelper.setFrom(workEmail, senderName);
        messageHelper.setTo(username);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
