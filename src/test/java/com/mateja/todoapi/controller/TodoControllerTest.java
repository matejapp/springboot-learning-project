package com.mateja.todoapi.controller;

import com.mateja.todoapi.dto.CreateTodoRequest;
import com.mateja.todoapi.dto.UpdateTodoRequest;
import com.mateja.todoapi.exception.TodoNotFoundException;
import com.mateja.todoapi.service.TodoService;
import com.mateja.todoapi.todo.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    private static final UUID ID =
            UUID.fromString("abf2e636-7ea6-4663-9355-1e829f9740ee");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Test
    void getAllTodos_returnsEmptyList() throws Exception {
        when(todoService.getAllTodos()).thenReturn(List.of());

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getTodoById_whenFound_returnsTodo() throws Exception {
        when(todoService.getTodoById(ID))
                .thenReturn(new Todo(ID, "Learn tests", false));

        mockMvc.perform(get("/api/todos/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.text").value("Learn tests"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void getTodoById_whenMissing_returns404() throws Exception {
        when(todoService.getTodoById(ID))
                .thenThrow(new TodoNotFoundException("Todo not found"));

        mockMvc.perform(get("/api/todos/{id}", ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("TodoNotFound"))
                .andExpect(jsonPath("$.detail").value("Todo not found"))
                .andExpect(jsonPath("$.instance").value("/api/todos/" + ID));
    }

    @Test
    void createTodo_withValidBody_returns201() throws Exception {
        when(todoService.createTodo(any(CreateTodoRequest.class)))
                .thenReturn(new Todo(ID, "Learn tests", false));

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"text":"Learn tests"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.text").value("Learn tests"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void createTodo_withBlankText_returns400() throws Exception {
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"text":"   "}
                                """))
                .andExpect(status().isBadRequest());

        verify(todoService, never()).createTodo(any(CreateTodoRequest.class));
    }

    @Test
    void updateTodo_withValidBody_returnsUpdatedTodo() throws Exception {
        when(todoService.updateTodoById(eq(ID), any(UpdateTodoRequest.class)))
                .thenReturn(new Todo(ID, "Updated", true));

        mockMvc.perform(put("/api/todos/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"text":"Updated","completed":true}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Updated"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void updateTodo_withBlankText_returns400() throws Exception {
        mockMvc.perform(put("/api/todos/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"text":"","completed":false}
                                """))
                .andExpect(status().isBadRequest());

        verify(todoService, never())
                .updateTodoById(eq(ID), any(UpdateTodoRequest.class));
    }

    @Test
    void updateTodo_whenMissing_returns404() throws Exception {
        when(todoService.updateTodoById(eq(ID), any(UpdateTodoRequest.class)))
                .thenThrow(new TodoNotFoundException("Todo not found"));

        mockMvc.perform(put("/api/todos/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"text":"Updated","completed":true}
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTodo_whenFound_returns204() throws Exception {
        mockMvc.perform(delete("/api/todos/{id}", ID))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(todoService).deleteTodoById(ID);
    }

    @Test
    void deleteTodo_whenMissing_returns404() throws Exception {
        doThrow(new TodoNotFoundException("Todo not found"))
                .when(todoService).deleteTodoById(ID);

        mockMvc.perform(delete("/api/todos/{id}", ID))
                .andExpect(status().isNotFound());
    }
}