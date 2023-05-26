package com.bureaucracyhacks.refactorip.exceptions;

public class ImageDimensionException extends RuntimeException {
    public ImageDimensionException() {
        super("The Image you try to add has more than 10MB!");
    }
}
