package org.psk.lab.exceptions.variable;

public record ApiError(String message,
                       int statusCode,
                       String timestamp) {
}
