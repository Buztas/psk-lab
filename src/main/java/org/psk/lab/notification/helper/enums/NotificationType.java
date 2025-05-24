package org.psk.lab.notification.helper.enums;

import lombok.Getter;

@Getter
public enum NotificationType {
    EMAIL("EMAIL");

    private final String type;

    NotificationType(String type) {
        this.type = type;
    }
}
