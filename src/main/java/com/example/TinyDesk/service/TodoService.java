package com.example.TinyDesk.service;

import com.example.TinyDesk.model.Todo;
import com.example.TinyDesk.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> findAll() {
        return repository.findAll();
    }

    public Todo create(String title) {
        if (title == null || title.trim().length() < 3) {
            throw new IllegalArgumentException("Title must be at least 3 characters");
        }
        Todo todo = new Todo(null, title.trim(), false);
        return repository.save(todo);
    }

    public Optional<Todo> toggle(Long id) {
        Optional<Todo> todoOpt = repository.findById(id);
        todoOpt.ifPresent(todo -> todo.setDone(!todo.isDone()));
        return todoOpt;
    }

    public boolean delete(Long id) {
        return repository.deleteById(id);
    }
}