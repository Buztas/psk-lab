package org.psk.lab.exceptions;

import org.psk.lab.exceptions.variable.ApiError;
import org.psk.lab.user.exception.InvalidUserCredentials;
import org.psk.lab.user.exception.UserAlreadyExistsException;
import org.psk.lab.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}