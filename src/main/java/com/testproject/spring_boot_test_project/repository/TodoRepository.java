package com.testproject.spring_boot_test_project.repository;

import com.testproject.spring_boot_test_project.model.Todo;
import com.testproject.spring_boot_test_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Todo entity.
 * Extends JpaRepository to inherit standard CRUD operations such as save, findById, delete, update, etc..
 * Custom query method findByOwner is needed for multi-user data isolation:
 * - Returns only todos belonging to the specified user.
 * - Used in the controller to enforce that users can only see/interact with their own todos.
 * Spring Data JPA automatically implements this method based on the name:
 * generates a query like "SELECT_FROM_Todo_WHERE_owner = X"
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {

    /**
     * Finds all todos owned by the given user.
     * @param owner the authenticated User
     * @return List of Todos belonging to that user. If empty returns an empty list.
     */
    List<Todo> findByOwner(User owner);
}
