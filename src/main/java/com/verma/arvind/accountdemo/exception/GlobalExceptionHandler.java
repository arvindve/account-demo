package com.verma.arvind.accountdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(BankAccountNotFoundException ex) {
        HttpStatus badRequest = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(ex.getMessage(), badRequest, LocalDateTime.now());
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(AccountAlreadyExistException.class)
    public ResponseEntity<Object> handleUserNotFound(AccountAlreadyExistException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(ex.getMessage(), badRequest, LocalDateTime.now());
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Object> handleUserNotFound(InsufficientFundsException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(ex.getMessage(), badRequest, LocalDateTime.now());
        return new ResponseEntity<>(apiException, badRequest);
    }


}
