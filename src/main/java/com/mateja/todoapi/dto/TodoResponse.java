package com.mateja.todoapi.dto;

import com.mateja.todoapi.todo.Todo;

import java.util.UUID;

public record TodoResponse(
        UUID id,
        String text,
        boolean completed
) {
    public static TodoResponse from(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getText(),
                todo.isCompleted()
        );
    }
}
