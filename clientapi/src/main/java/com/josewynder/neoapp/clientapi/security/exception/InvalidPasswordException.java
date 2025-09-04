package com.josewynder.neoapp.clientapi.security.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Invalid password");
    }

}
