package com.josewynder.neoapp.clientapi.exception;

public class ClientDuplicateException extends RuntimeException {

    public ClientDuplicateException(String field, String value) {
        super("Client with " + field + " '" + value + "' already exists.");
    }
}
