package com.anastas.carsshop.excaption;

public class BadRequestContentException extends RuntimeException {
    public BadRequestContentException(String message) {super(message);}

    public BadRequestContentException() {super("Bad request body!");}
}
