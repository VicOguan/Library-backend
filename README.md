# 📚 Ohara Digital Library System — REST API Backend

![Java 17](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-Stateless-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Authentication-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-Relational_DB-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

A high-performance, secure backend RESTful service for an open-access Digital Library management system. Built with **Spring Boot 3**, **Spring Security**, **Spring Data JPA**, and **Stateless JWT Authentication**.

---

## 🏛️ System Architecture & Backend Highlights

The backend follows clean layered architecture principles (*Controller ➔ Service ➔ Repository ➔ Entity*), decoupling business logic from web routing and data persistence layers.

### 🔐 Security & Access Control
- **Stateless Session Management:** Standard HTTP sessions are completely disabled (`SessionCreationPolicy.STATELESS`).
- **Custom JWT Filter (`JwtAuthFilter`):** Intercepts incoming requests to validate signed Bearer tokens and inject authenticated details into the `SecurityContextHolder`.
- **Role-Based Access Control (RBAC):**
  - `ROLE_USER`: Can view book availability, search volumes, check out available books, and inspect personal loan histories.
  - `ROLE_ADMIN`: Grants elevated administrative privileges to add, edit, or remove catalog items.
- **BCrypt Password Encoding:** User passwords are encrypted prior to database insertion using `PasswordEncoder`.

### ⚡ Domain Rules & Exception Management
- **Centralized Exception Handling (`GlobalExceptionHandler`):** Converts domain-specific exceptions into standard `@RestControllerAdvice` JSON error payloads (`ErrorResponse`).
- **Domain Constraints Enforced:**
  - `BookAlreadyBorrowedException`: Prevents simultaneous active loans of the same physical volume.
  - `MaxBorrowLimitExceededException`: Restricts individual users from exceeding maximum open loan quotas.
  - `BookNotFoundException` & `UserNotFoundException`: Gracefully handles missing database entities.
- **Automated Data Seeding (`DataInitializer`):** Pre-populates administrative and member accounts alongside starter catalog volumes during local application startup.

---

## 🛠️ Technology Stack & Dependencies

- **Language:** Java 17
- **Framework:** Spring Boot 3.x
- **Security:** Spring Security, `jjwt` (Java JWT)
- **Persistence & ORM:** Spring Data JPA / Hibernate
- **Database:** MySQL
- **Tooling & Build:** Apache Maven (`mvnw`), Lombok

---

## 📂 Project Structure

```text
com.tutorial.study
├── config/             # Spring SecurityFilterChain & CORS configurations
├── controller/         # REST Controllers (AuthController, BookController, BorrowController)
├── dto/                # Data Transfer Objects (Requests, Responses, API Wrappers)
├── entity/             # JPA Entities (AppUser, Book, BorrowRecord)
├── exception/          # Custom exceptions & @RestControllerAdvice handler
├── initializer/        # Bootstrapping initial database seeds
├── mapper/             # Entity-to-DTO conversion layers
├── repository/         # Spring Data JPA interfaces
├── security/           # Custom JwtAuthFilter implementation
└── service/            # Core business logic & JWT generation services
