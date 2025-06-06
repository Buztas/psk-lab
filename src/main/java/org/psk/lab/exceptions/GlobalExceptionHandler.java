package org.psk.lab.exceptions;

import org.psk.lab.exceptions.variable.ApiError;
import org.psk.lab.order.exception.InvalidStatusValueException;
import org.psk.lab.order.exception.OptimisticLockingConflictException;
import org.psk.lab.order.exception.OrderNotFoundException;
import org.psk.lab.payment.exception.OptimisticPaymentLockException;
import org.psk.lab.payment.exception.PaymentNotFoundException;
import org.psk.lab.user.exception.InvalidUserCredentials;
import org.psk.lab.user.exception.UserAlreadyExistsException;
import org.psk.lab.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        var error = new ApiError(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidUserCredentials.class})
    public ResponseEntity<?> handleInvalidUserCredentials(InvalidUserCredentials ex) {
        var error = new ApiError(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<?> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        var error = new ApiError(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException ex, WebRequest request) {
        var error = new ApiError(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({OptimisticLockingConflictException.class})
    public ResponseEntity<?> handleOptimisticLockingConflictException(OptimisticLockingConflictException ex, WebRequest request) {
        var error = new ApiError(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InvalidStatusValueException.class})
    public ResponseEntity<?> handleInvalidStatusValueException(InvalidStatusValueException ex, WebRequest request) {
        var error = new ApiError(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<String> handlePaymentNotFound(PaymentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(OptimisticPaymentLockException.class)
    public ResponseEntity<String> handleOptimisticLock(OptimisticPaymentLockException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}