package org.psk.lab.notification.config;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class MailConfig {

    @Bean
    public JavaMailSenderImpl javaMailSender(MailProperties properties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(properties.getHost());
        mailSender.setPort(properties.getPort());
        mailSender.setUsername(properties.getUsername());
        mailSender.setPassword(properties.getPassword());

        Properties javaMailProps = mailSender.getJavaMailProperties();
        javaMailProps.put("mail.smtp.auth", properties.getProperties().get("mail.smtp.auth"));
        javaMailProps.put("mail.smtp.starttls.enable", properties.getProperties().get("mail.smtp.starttls.enable"));
        javaMailProps.put("mail.smtp.connectiontimeout", properties.getProperties().get("mail.smtp.connectiontimeout"));
        javaMailProps.put("mail.smtp.timeout", properties.getProperties().get("mail.smtp.timeout"));
        javaMailProps.put("mail.smtp.writetimeout", properties.getProperties().get("mail.smtp.writetimeout"));

        javaMailProps.put("mail.smtp.starttls.required", "true");
        javaMailProps.put("mail.smtp.ssl.trust", properties.getHost());
        javaMailProps.put("mail.debug", "false");

        return mailSender;
    }
}
