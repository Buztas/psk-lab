package org.psk.lab.notification.helper.exceptions;

public class FaultyNotificationException extends RuntimeException {
    public FaultyNotificationException(String msg) {
        super(String.format("Faulty notification: %s", msg));
    }
}
