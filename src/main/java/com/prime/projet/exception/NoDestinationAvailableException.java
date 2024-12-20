package com.prime.projet.exception;

public class NoDestinationAvailableException extends RuntimeException {
    public NoDestinationAvailableException(String message) {
        super(message);
    }
}