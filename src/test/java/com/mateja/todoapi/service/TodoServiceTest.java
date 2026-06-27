package com.mateja.todoapi.service;

import com.mateja.todoapi.dto.CreateTodoRequest;
import com.mateja.todoapi.dto.UpdateTodoRequest;
import com.mateja.todoapi.exception.TodoNotFoundException;
import com.mateja.todoapi.repo.TodoRepo;
import com.mateja.todoapi.todo.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    private static final UUID ID =
            UUID.fromString("abf2e636-7ea6-4663-9355-1e829f9740ee");

    @Mock
    private TodoRepo repo;

    @InjectMocks
    private TodoService service;

    @Test
    void getAllTodos_returnsRepositoryResults() {
        List<Todo> expected = List.of(new Todo(ID, "Test", false));
        when(repo.findAll()).thenReturn(expected);

        assertSame(expected, service.getAllTodos());
    }

    @Test
    void getTodoById_whenFound_returnsTodo() {
        Todo expected = new Todo(ID, "Test", false);
        when(repo.findById(ID)).thenReturn(Optional.of(expected));

        assertSame(expected, service.getTodoById(ID));
    }

    @Test
    void getTodoById_whenMissing_throwsTodoNotFoundException() {
        when(repo.findById(ID)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> service.getTodoById(ID));
    }

    @Test
    void createTodo_savesTodoWithCorrectValues() {
        CreateTodoRequest request = new CreateTodoRequest();
        request.setText("Created");
        when(repo.save(any(Todo.class))).thenAnswer(call -> call.getArgument(0));

        Todo result = service.createTodo(request);

        ArgumentCaptor<Todo> captor = ArgumentCaptor.forClass(Todo.class);
        verify(repo).save(captor.capture());
        assertSame(captor.getValue(), result);
        assertNull(result.getId());
        assertEquals("Created", result.getText());
        assertFalse(result.isCompleted());
    }

    @Test
    void updateTodo_changesExistingTodo() {
        Todo existing = new Todo(ID, "Before", false);
        when(repo.findById(ID)).thenReturn(Optional.of(existing));
        UpdateTodoRequest request = new UpdateTodoRequest("After", true);

        Todo result = service.updateTodoById(ID, request);

        assertSame(existing, result);
        assertEquals("After", result.getText());
        assertTrue(result.isCompleted());
    }

    @Test
    void deleteTodo_deletesExistingTodo() {
        Todo existing = new Todo(ID, "Delete", false);
        when(repo.findById(ID)).thenReturn(Optional.of(existing));

        service.deleteTodoById(ID);

        verify(repo).delete(existing);
    }
}