package org.psk.lab.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OptimisticPaymentLockException extends RuntimeException {

    public OptimisticPaymentLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
