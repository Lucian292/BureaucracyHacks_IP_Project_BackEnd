package com.bureaucracyhacks.refactorip.exceptions;

public class InstitutionNotFoundException extends RuntimeException {
    public InstitutionNotFoundException(String error) {
        super(error);
    }
}
