package com.mateja.todoapi.service;


import com.mateja.todoapi.dto.CreateTodoRequest;
import com.mateja.todoapi.dto.UpdateTodoRequest;
import com.mateja.todoapi.exception.TodoNotFoundException;
import com.mateja.todoapi.repo.TodoRepo;
import com.mateja.todoapi.todo.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;



import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepo repo;

    public TodoService(TodoRepo repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Todo> getAllTodos() {

        return repo.findAll();

    }


    @Transactional(readOnly = true)
    public Todo getTodoById(UUID id) {



        return repo.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo not found"));

    }

    @Transactional
    public Todo createTodo(CreateTodoRequest createTodoRequest) {
        Todo newTodo = new Todo(createTodoRequest.getText(), false);

        return repo.save(newTodo);


    }

    @Transactional
    public void deleteTodoById(UUID id) {

        Todo todoToDelete = getTodoById(id);

        repo.delete(todoToDelete);

    }

    @Transactional
    public Todo updateTodoById(UUID id, UpdateTodoRequest updateTodoRequest)
    {
        Todo  todoToUpdate = getTodoById(id);
        todoToUpdate.setText(updateTodoRequest.getText());
        todoToUpdate.setCompleted(updateTodoRequest.isCompleted());
        return todoToUpdate;

    }
}
