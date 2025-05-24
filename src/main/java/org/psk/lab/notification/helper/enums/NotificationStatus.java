package org.psk.lab.notification.helper.enums;

import lombok.Getter;

@Getter
public enum NotificationStatus {
    PENDING("PENDING"),
    SENT("SENT"),
    FAILED("FAILED");

    private final String status;

    NotificationStatus(String status) {
        this.status = status;
    }
}
