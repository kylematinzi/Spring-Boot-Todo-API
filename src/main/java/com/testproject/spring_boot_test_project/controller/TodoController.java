package com.testproject.spring_boot_test_project.controller;

import com.testproject.spring_boot_test_project.model.Todo;
import com.testproject.spring_boot_test_project.repository.TodoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    // GET all todos
    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // POST - create new todo with proper 201 Created
    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        Todo saved = todoRepository.save(todo);
        return ResponseEntity
                .created(URI.create("/api/todos/" + saved.getId()))
                .body(saved);
    }

    // DELETE - delete the selected id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id){
        if (!todoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();  // 404 if not found
        }
        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();  // 204 No Content on success
    }

    //UPDATE - update todo by ID
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        if (!todoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();  // 404 if not found
        }
        // Keep the original ID, copy new values. New values are Title and Completion status.
        updatedTodo.setId(id);
        Todo saved = todoRepository.save(updatedTodo);
        return ResponseEntity.ok(saved);  // 200 OK with updated todo
    }

    // GET single todo by ID
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return todoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
