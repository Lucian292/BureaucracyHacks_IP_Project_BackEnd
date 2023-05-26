package com.bureaucracyhacks.refactorip.exceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException() {
        super("Task Not Found !");
    }
}
