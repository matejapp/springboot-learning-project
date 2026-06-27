package com.mateja.todoapi.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateTodoRequest {

    @NotBlank
    private String text;

    private boolean completed;

    public UpdateTodoRequest(String text, boolean completed) {

        this.text = text;
        this.completed = completed;

    }

    public  UpdateTodoRequest() {}


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
