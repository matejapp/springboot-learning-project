package com.mateja.todoapi.todo;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private boolean completed = false;

    public Todo(String text, boolean completed) {

        this.text = text;
        this.completed = completed;
    }

    public Todo(){}

    public Todo(UUID id, String learnControllerTests, boolean b) {

        this.id = id;
        this.text = learnControllerTests;
        this.completed = b;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
