package com.testproject.spring_boot_test_project.repository;

import com.testproject.spring_boot_test_project.model.Todo;
import com.testproject.spring_boot_test_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByOwner(User owner);
}
