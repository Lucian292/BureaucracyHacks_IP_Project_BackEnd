package com.bureaucracyhacks.refactorip.exceptions;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException() {
        super("Image not found!");
    }
}
