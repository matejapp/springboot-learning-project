package com.mateja.todoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.ModelAttribute;


public class CreateTodoRequest {

    @NotBlank(message = "Todo must have text")
    private String text;

    public CreateTodoRequest(String text) {
        this.text = text;
    }

    public CreateTodoRequest() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
