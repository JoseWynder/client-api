package com.josewynder.neoapp.clientapi.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(Long clientId) {
        super("Client with id '" + clientId + "' not found.");
    }

}
