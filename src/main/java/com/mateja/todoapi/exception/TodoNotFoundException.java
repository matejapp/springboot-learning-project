package com.mateja.todoapi.exception;

public class TodoNotFoundException extends  RuntimeException {
    public TodoNotFoundException(String message) {
        super(message);
    }
}
