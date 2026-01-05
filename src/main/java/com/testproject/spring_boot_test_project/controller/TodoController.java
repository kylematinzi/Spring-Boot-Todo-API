package com.testproject.spring_boot_test_project.controller;

import com.testproject.spring_boot_test_project.model.Todo;
import com.testproject.spring_boot_test_project.model.User;
import com.testproject.spring_boot_test_project.repository.TodoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller for todo operations.
 * All endpoints are protected by JWT authentication, this is configured in SecurityConfig.
 * Enforces user ownership:
 * - Users can only access, modify, or delete todos they own.
 * - Ownership is checked explicitly for single-item operations.
 * - List endpoint is filtered at the repository level for efficiency.
 */
@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    /**
     * Retrieves the currently authenticated user from the SecurityContext.
     * The principal is our User entity, implements user details.
     * @SuppressWarnings used because authentication is guaranteed for protected endpoints.
     */
    @SuppressWarnings("ConstantConditions")
    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Returns all todos belonging to the authenticated user.
     */
    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findByOwner(getCurrentUser());
    }

    /**
     * Creates a new todo for the authenticated user.
     * - Validates input with @Valid.
     * - Automatically assigns the current user as owner.
     * - Returns 201 Created with Location header.
     */
    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        todo.setOwner(getCurrentUser());
        Todo saved = todoRepository.save(todo);
        return ResponseEntity
                .created(URI.create("/api/todos/" + saved.getId()))
                .body(saved);
    }

    /**
     * Deletes a todo by ID.
     * Returns 404 if not found, 403 Forbidden if not owned by the current user.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id){
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getOwner().equals(getCurrentUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates a todo by ID.
     * - Validates ownership before updating.
     * - Does not change the owner.
     * - Returns 404 if not found, 403 if not owner
     */
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getOwner().equals(getCurrentUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        updatedTodo.setId(id);
        updatedTodo.setOwner(getCurrentUser());
        Todo saved = todoRepository.save(updatedTodo);
        return ResponseEntity.ok(saved);
    }

    /**
     * Retrieves a single todo by ID.
     * Returns 404 if not found, 403 Forbidden if not owned by the current user.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getOwner().equals(getCurrentUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(todo);
    }
}
