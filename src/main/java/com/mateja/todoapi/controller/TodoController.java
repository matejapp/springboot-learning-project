package com.mateja.todoapi.controller;

import com.mateja.todoapi.dto.CreateTodoRequest;
import com.mateja.todoapi.dto.TodoResponse;
import com.mateja.todoapi.dto.UpdateTodoRequest;
import com.mateja.todoapi.service.TodoService;
import com.mateja.todoapi.todo.Todo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public  TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoResponse> getAllTodos() {

        return todoService.getAllTodos()
                .stream()
                .map(TodoResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoResponse getTodoById(@PathVariable UUID id) {
        Todo todo =  todoService.getTodoById(id);
        return TodoResponse.from(todo);


    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse createTodo(
            @Valid @RequestBody CreateTodoRequest request) {

        Todo createdTodo = todoService.createTodo(request);
        return TodoResponse.from(createdTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable UUID id) {

        todoService.deleteTodoById(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoResponse updateTodo(@PathVariable UUID id, @Valid @RequestBody UpdateTodoRequest request) {

        Todo updatedTodo = todoService.updateTodoById(id, request);
        return TodoResponse.from(updatedTodo);

    }



}
