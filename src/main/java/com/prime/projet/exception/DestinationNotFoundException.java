package com.prime.projet.exception;

public class DestinationNotFoundException extends RuntimeException {
    public DestinationNotFoundException(String message) {
        super(message);
    }
}