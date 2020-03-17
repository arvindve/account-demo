package com.verma.arvind.accountdemo.exception;

public class AccountAlreadyExistException extends RuntimeException {

    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
