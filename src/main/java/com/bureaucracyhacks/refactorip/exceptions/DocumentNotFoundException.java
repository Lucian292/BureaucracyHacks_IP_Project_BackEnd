package com.bureaucracyhacks.refactorip.exceptions;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException() {
        super("User Not Found !");
    }
}
