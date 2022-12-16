package com.domo.ecommerce.exception;

public class NotAdminLoginException extends RuntimeException {

    public NotAdminLoginException(String message) {
        super(message);
    }
}
