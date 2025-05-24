package org.psk.lab.notification.domain.strategies;

import org.psk.lab.notification.domain.dtos.NotificationDto;
import org.psk.lab.notification.helper.enums.NotificationType;

public interface NotificationStrategy {
    NotificationType getType();
    boolean send(NotificationDto notificationDto);
}
