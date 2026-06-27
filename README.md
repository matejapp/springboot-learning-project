# Todo API

This is my first Spring Boot web application. I built it as a learning project to become comfortable with the Spring ecosystem and to apply backend concepts I already knew from .NET, including layered architecture, REST, validation, persistence, and automated testing.

The project is a small REST API for creating and managing todo items. Although the domain is intentionally simple, the application uses patterns and tools commonly found in larger Spring Boot services.

## Features

- Create, read, update, and delete todos
- Request validation with Jakarta Bean Validation
- PostgreSQL persistence through Spring Data JPA and Hibernate
- Database schema migrations with Flyway
- UUID identifiers
- Transactional service operations
- RFC 9457 error responses with Spring `ProblemDetail`
- Controller and service tests with JUnit, MockMvc, and Mockito
- Separate request and response DTOs

## Tech Stack

- Java 17+
- Spring Boot 4
- Spring MVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Flyway
- Maven
- JUnit and Mockito

## Requirements

Before running the application, install:

- Java 17 or newer
- PostgreSQL
- Maven, or use the included Maven wrapper

Create an empty PostgreSQL database. Flyway will create the required tables when the application starts.

## Configuration

The application reads its database connection from environment variables:

| Variable | Example | Description |
| --- | --- | --- |
| `DB_URL` | `jdbc:postgresql://localhost:5432/todo_db` | PostgreSQL JDBC URL |
| `DB_USERNAME` | `postgres` | Database username |
| `DB_PASSWORD` | `your-password` | Database password |

Example for PowerShell:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/todo_db"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your-password"
```

The same variables can be configured in the IntelliJ IDEA Spring Boot run configuration.

> [!IMPORTANT]
> Do not commit real database credentials to the repository.

## Running the Application

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

The API starts at `http://localhost:8080`.

Flyway applies any pending migrations before Hibernate validates the entity mappings.

## API Endpoints

| Method | Endpoint | Description | Success status |
| --- | --- | --- | --- |
| `GET` | `/api/todos` | Get all todos | `200 OK` |
| `GET` | `/api/todos/{id}` | Get one todo | `200 OK` |
| `POST` | `/api/todos` | Create a todo | `201 Created` |
| `PUT` | `/api/todos/{id}` | Update a todo | `200 OK` |
| `DELETE` | `/api/todos/{id}` | Delete a todo | `204 No Content` |

### Create a Todo

```http
POST /api/todos
Content-Type: application/json

{
  "text": "Learn Spring Boot"
}