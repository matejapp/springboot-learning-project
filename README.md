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
```

### Update a Todo

```http
PUT /api/todos/{id}
Content-Type: application/json

{
  "text": "Finish the Todo API",
  "completed": true
}
```

Blank todo text returns `400 Bad Request`. Requesting a todo that does not exist returns an `application/problem+json` response with status `404 Not Found`.

## Project Structure

```text
controller  HTTP endpoints and response mapping
service     application logic and transaction boundaries
repo        Spring Data JPA repositories
todo        JPA entity
dto         request and response models
exception   custom exceptions and global error handling
```

Database migrations are stored in `src/main/resources/db/migration`.

## Running Tests

On Windows:

```powershell
.\mvnw.cmd test
```

On macOS or Linux:

```bash
./mvnw test
```

Controller tests use MockMvc and a mocked service, so they do not start a real HTTP server or connect to PostgreSQL. Service tests use Mockito to isolate the repository dependency.

## What I Learned

This project helped me practice:

- Spring dependency injection and constructor injection
- Mapping HTTP requests with Spring MVC
- DTO validation and JSON serialization
- Layered application structure
- JPA entities, repositories, transactions, and dirty checking
- PostgreSQL configuration and connection pooling
- Versioned database migrations
- Global exception handling
- Controller and service testing

## Contributing

This is a personal learning project, but feedback and suggestions are welcome through issues or pull requests.

## License

No license has been selected for this project yet.