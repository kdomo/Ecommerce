package com.domo.ecommerce.exception;

public class LoginFailException extends RuntimeException {

    public LoginFailException(String message) {
        super(message);
    }
}
