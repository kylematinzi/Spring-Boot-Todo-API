## TODO API Created using Java / Spring Boot

This is a RESTful Todo API built with Spring Boot, Spring Data JPA, H2 in-memory database, Spring Validation, and Spring Security basics.
The API supports complete CRUD operations on todos with proper HTTP status codes, input validation, and clean error handling. 
It follows a layered architecture (model, repository, controller, exception). 

## What I Learned

- Building a complete CRUD REST API in Spring Boot
- Using Spring Data JPA repositories and entities
- Bean Validation with @NotBlank and @Valid
- Global exception handling with @RestControllerAdvice and MethodArgumentNotValidException
- Clean project structure and package organization

## Room For Improvement / Next Steps

- Add JWT authentication and user registration/login
- Associate todos with users (user ownership)
- Add role-based access (e.g., admin can view all)

## Why I built it

The goal of building this project was to continue building my backend foundation and to get closer to being able to build a secure, multi-user application like my upcoming FitForge gym management application.
