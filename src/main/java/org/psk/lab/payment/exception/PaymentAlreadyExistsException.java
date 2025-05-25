package org.psk.lab.payment.exception;

import java.util.UUID;

public class PaymentAlreadyExistsException extends RuntimeException {
    public PaymentAlreadyExistsException(UUID orderId) {
        super("Payment already exists for order ID: " + orderId);
    }
}
