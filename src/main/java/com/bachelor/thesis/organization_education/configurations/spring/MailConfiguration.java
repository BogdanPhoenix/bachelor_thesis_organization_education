package com.bachelor.thesis.organization_education.configurations.spring;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {
    @Bean
    public JavaMailSender getJavaMailSender() {
        var dotenv = Dotenv.configure().load();
        var mail = new JavaMailSenderImpl();
        var props = mail.getJavaMailProperties();
        var username = dotenv.get("MAIL_USERNAME");
        var password = dotenv.get("MAIL_PASSWORD");

        mail.setHost("smtp.gmail.com");
        mail.setPort(587);
        mail.setUsername(username);
        mail.setPassword(password);

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mail;
    }
}
