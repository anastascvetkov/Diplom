package com.anastas.carsshop.excaption;

public class BadLoginCredentialException extends RuntimeException {
    public BadLoginCredentialException(String message) {super(message);}

    public BadLoginCredentialException() {super("Bad login credentials!");}
}
