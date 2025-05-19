package org.psk.lab.order.exception;

public class OptimisticLockingConflictException extends RuntimeException {

    public OptimisticLockingConflictException(String message) {
        super(message);
    }

    public OptimisticLockingConflictException(String message, Throwable cause) {
      super(message, cause);
    }
}
