package com.bureaucracyhacks.refactorip.exceptions;

public class ImageFormatException extends RuntimeException{
    public ImageFormatException() {
        super("The file you try to add is not an image!(JPG,JPEG,PNG)");
    }
}
