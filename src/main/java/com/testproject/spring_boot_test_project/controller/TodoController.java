package com.testproject.spring_boot_test_project.controller;

import com.testproject.spring_boot_test_project.model.Todo;
import com.testproject.spring_boot_test_project.model.User;
import com.testproject.spring_boot_test_project.repository.TodoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    @SuppressWarnings("ConstantConditions")
    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // GET all todos
    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findByOwner(getCurrentUser());
    }

    // POST - create new todo with proper 201 Created
    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        todo.setOwner(getCurrentUser());// this sets the owner of the todo to the current user
        Todo saved = todoRepository.save(todo);
        return ResponseEntity
                .created(URI.create("/api/todos/" + saved.getId()))
                .body(saved);
    }

    // DELETE - delete the selected id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id){
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getOwner().equals(getCurrentUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // 403 if not owner
        }

        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //UPDATE - update todo by ID
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getOwner().equals(getCurrentUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // 403 if not owner
        }

        updatedTodo.setId(id);
        updatedTodo.setOwner(getCurrentUser());  // <-- Keep owner (safety)
        Todo saved = todoRepository.save(updatedTodo);
        return ResponseEntity.ok(saved);
    }

    // GET single todo by ID
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getOwner().equals(getCurrentUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // 403 if not owner
        }

        return ResponseEntity.ok(todo);
    }
}
