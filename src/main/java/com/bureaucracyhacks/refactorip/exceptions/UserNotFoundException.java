package com.bureaucracyhacks.refactorip.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User Not Found !");
    }
}
