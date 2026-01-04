## Secure Todo API – Built with Java & Spring Boot

This is a **fully secure, multi-user RESTful Todo API** built with Spring Boot, Spring Data JPA, H2 in-memory database, Spring Validation, and Spring Security with JWT authentication.

The API supports complete **CRUD operations** with:
- Proper HTTP status codes (201 Created, 204 No Content, 401 Unauthorized, 403 Forbidden, 404 Not Found)
- Input validation and clean, field-specific error messages
- **JWT-based stateless authentication** (registration, login, token validation)
- **User ownership and data isolation** — each user can only access, modify, or delete their own todos
- BCrypt password hashing and secure practices (no password leaks in responses)

It follows a clean, layered architecture:
- `model` – entities (User, Todo with ManyToOne ownership)
- `dto` – request/response objects
- `repository` – data access
- `service` – business logic (auth, JWT handling)
- `controller` – REST endpoints
- `config` – security and JWT filter
- `exception` – global error handling

## What I Learned

- Building a complete CRUD REST API in Spring Boot
- Using Spring Data JPA repositories and entities
- Bean Validation with @NotBlank and @Valid
- Global exception handling with @RestControllerAdvice and MethodArgumentNotValidException
- Clean project architecture: seperated DTO's, services, controllers, config, and entities with proper package organization
- Implementing user ownership and data isolation. This ensures users can only access, modify, and/or delete their own todos
- JWT based authentication using JJWT - generates tokens and validation

## Features
- JWT authentication (registration/login)
- Protected endpoints with Bearer token
- User-specific todos (data isolation)
- BCrypt password hashing
- Validation and global error handling

## Security
- Only authenticated users can access /api/todos/**
- Users can only view/edit/delete their own todos

## Why I built it

The goal of building this project was to continue building my backend foundation and to get closer to being able to build a secure, multi-user application like my upcoming FitForge gym management application.This project evolved from a simple Todo list into a production-ready, secure backend.
