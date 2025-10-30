package com.ayush.microservices.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
