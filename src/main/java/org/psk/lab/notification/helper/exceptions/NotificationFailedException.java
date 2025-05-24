package org.psk.lab.notification.helper.exceptions;

import org.psk.lab.notification.helper.enums.NotificationType;

import java.util.UUID;

public class NotificationFailedException extends RuntimeException{
    public NotificationFailedException(NotificationType type, UUID notificationId, String message) {
        super(String.format("Notification failed (id: %s, type: %s), message: %s", notificationId, type.getType(), message));
    }
}
