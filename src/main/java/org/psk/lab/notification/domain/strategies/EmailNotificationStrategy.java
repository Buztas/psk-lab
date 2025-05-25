package org.psk.lab.notification.domain.strategies;

import lombok.RequiredArgsConstructor;
import org.psk.lab.notification.domain.dtos.NotificationDto;
import org.psk.lab.notification.helper.enums.NotificationType;
import org.psk.lab.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class EmailNotificationStrategy implements NotificationStrategy{
    private static final Logger log = LoggerFactory.getLogger(EmailNotificationStrategy.class);

    private final JavaMailSenderImpl mailSender;
    private final OrderService orderService;

    @Override
    public NotificationType getType(){
        return NotificationType.EMAIL;
    }

    @Override
    public boolean send(NotificationDto notificationDto) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mailSender.getUsername());

        // Actual user email, will not work with the current setup
        // MyUser user = orderService.getUserByOrderId(notificationDto.orderId());
        // msg.setTo(user.getEmail());

        // Hardcoded email for testing
        msg.setTo("kavapp.kavapp@gmail.com");
        msg.setSubject("Order # " + notificationDto.orderId() + " is ready");
        msg.setText(notificationDto.message());

        try {
            mailSender.send(msg);
            log.info("Email sent to {}", Arrays.toString(msg.getTo()));
            return true;
        } catch (MailAuthenticationException authEx) {
            log.error("Authentication failed for {}: {}", mailSender.getUsername(), authEx.getMessage(), authEx);
        } catch (MailException ex) {
            log.error("Unexpected error sending email to {}: {}", msg.getTo(), ex.getMessage(), ex);
        }

        return false;
    }
}
