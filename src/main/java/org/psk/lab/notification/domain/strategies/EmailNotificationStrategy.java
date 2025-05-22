package org.psk.lab.notification.domain.strategies;

import lombok.RequiredArgsConstructor;
import org.psk.lab.notification.domain.dtos.NotificationDto;
import org.psk.lab.notification.helper.enums.NotificationType;
import org.psk.lab.order.service.OrderService;
import org.psk.lab.user.data.model.MyUser;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationStrategy implements NotificationStrategy{
    private final JavaMailSender mailSender;
    private final OrderService orderService;

    @Override
    public NotificationType getType(){
        return NotificationType.EMAIL;
    }

    @Override
    public boolean send(NotificationDto notificationDto) {
        SimpleMailMessage msg = new SimpleMailMessage();
        MyUser user = orderService.getUserByOrderId(notificationDto.orderId());
        msg.setTo(user.getEmail());
        msg.setSubject("Order # " + notificationDto.orderId() + " is ready");
        msg.setText(notificationDto.message());
        try {
            mailSender.send(msg);
            return true;
        } catch (MailException e) {
            // todo Handle the exception
            return false;
        }
    }
}
