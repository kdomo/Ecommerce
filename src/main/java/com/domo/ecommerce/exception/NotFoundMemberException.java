package com.domo.ecommerce.exception;

public class NotFoundMemberException extends RuntimeException {

    public NotFoundMemberException(String message) {
        super(message);
    }
}
