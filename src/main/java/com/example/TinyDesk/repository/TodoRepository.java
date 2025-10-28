package com.example.TinyDesk.repository;

import com.example.TinyDesk.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TodoRepository {
    private final List<Todo> todos = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Todo> findAll() {
        return new ArrayList<>(todos);
    }

    public Optional<Todo> findById(Long id) {
        return todos.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst();
    }

    public Todo save(Todo todo) {
        if (todo.getId() == null) {
            todo.setId(idGenerator.getAndIncrement());
        }
        todos.add(todo);
        return todo;
    }

    public boolean deleteById(Long id) {
        return todos.removeIf(todo -> todo.getId().equals(id));
    }
}