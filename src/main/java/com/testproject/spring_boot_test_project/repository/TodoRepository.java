package com.testproject.spring_boot_test_project.repository;

import com.testproject.spring_boot_test_project.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
