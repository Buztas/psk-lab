package org.psk.lab.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String uuid) {
        super("User with id: %s not found".formatted(uuid));
    }
}
