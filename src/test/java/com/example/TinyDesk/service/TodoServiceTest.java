package com.example.TinyDesk.service;

import com.example.TinyDesk.model.Todo;
import com.example.TinyDesk.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TodoServiceTest {
    private TodoService service;
    private TodoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TodoRepository();
        service = new TodoService(repository);
    }

    @Test
    void createWithValidTitle() {
        Todo todo = service.create("Learn Spring Boot");
        
        assertNotNull(todo.getId());
        assertEquals("Learn Spring Boot", todo.getTitle());
        assertFalse(todo.isDone());
    }

    @Test
    void createWithShortTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.create("ab");
        });
    }

    @Test
    void createWithEmptyTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.create("");
        });
    }

    @Test
    void toggleExistingTodo() {
        Todo todo = service.create("Test task");
        Long todoId = todo.getId();
        
        Optional<Todo> toggled = service.toggle(todoId);
        
        assertTrue(toggled.isPresent());
        assertTrue(toggled.get().isDone());
        
        Optional<Todo> toggledAgain = service.toggle(todoId);
        assertTrue(toggledAgain.isPresent());
        assertFalse(toggledAgain.get().isDone());
    }

    @Test
    void toggleNonExistingTodo() {
        Optional<Todo> result = service.toggle(999L);
        
        assertFalse(result.isPresent());
    }

    @Test
    void deleteExistingTodo() {
        Todo todo = service.create("Task to delete");
        
        boolean deleted = service.delete(todo.getId());
        
        assertTrue(deleted);
        assertEquals(0, service.findAll().size());
    }

    @Test
    void deleteNonExistingTodo() {
        boolean deleted = service.delete(999L);
        
        assertFalse(deleted);
    }
}