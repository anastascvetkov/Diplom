package com.anastas.carsshop.excaption;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException() {
        super("Incorrect password");
    }
}
