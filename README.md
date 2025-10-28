# TinyTasks

A modern, minimalist task management application built with Spring Boot and vanilla JavaScript.

## Overview

TinyTasks is a lightweight todo application featuring a clean dark-mode interface and a RESTful API backend. Built with simplicity and performance in mind, it provides essential task management functionality without unnecessary complexity.

## Features

- **Create Tasks** - Add new tasks with descriptive titles
- **Mark Complete** - Toggle tasks between completed and pending states
- **Delete Tasks** - Remove tasks you no longer need
- **Dark Mode UI** - Modern, professional interface inspired by GitHub's design
- **Real-time Updates** - Instant feedback on all operations
- **Input Validation** - Client and server-side validation for data integrity
- **Responsive Design** - Works seamlessly on desktop and mobile devices

## Tech Stack

### Backend
- **Java 21** - Latest LTS version with modern language features
- **Spring Boot 3.2.0** - Industry-standard framework for Java applications
- **Maven** - Dependency management and build automation
- **In-Memory Storage** - Simple ArrayList-based repository (suitable for development)

### Frontend
- **HTML5/CSS3** - Modern semantic markup and styling
- **Vanilla JavaScript** - No frameworks, pure ES6+ code
- **Bootstrap 5** - Responsive grid and components
- **Fetch API** - Asynchronous HTTP requests

## Project Structure

```
tinytasks/
├── src/
│   ├── main/
│   │   ├── java/com/example/TinyDesk/
│   │   │   ├── TinyTasksApplication.java      # Application entry point
│   │   │   ├── config/
│   │   │   │   └── CorsConfig.java            # CORS configuration
│   │   │   ├── controller/
│   │   │   │   └── TodoController.java        # REST API endpoints
│   │   │   ├── model/
│   │   │   │   └── Todo.java                  # Task entity
│   │   │   ├── repository/
│   │   │   │   └── TodoRepository.java        # Data access layer
│   │   │   └── service/
│   │   │       └── TodoService.java           # Business logic
│   │   ├── frontend/
│   │   │   ├── index.html                     # Main HTML page
│   │   │   ├── style.css                      # Custom styles
│   │   │   └── app.js                         # Frontend logic
│   │   └── resources/
│   │       └── application.properties         # App configuration
│   └── test/
│       └── java/com/example/TinyDesk/
│           ├── TinyDeskApplicationTests.java
│           └── service/
│               └── TodoServiceTest.java       # Service unit tests
├── pom.xml                                    # Maven configuration
└── README.md
```

## Prerequisites

- **Java 21** or higher
- **Maven 3.9+** (or use included Maven wrapper)
- A modern web browser
- (Optional) A local web server for the frontend (e.g., Live Server)

## Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd tinytasks
```

### 2. Build the Project

Using Maven wrapper (recommended):
```bash
./mvnw clean install
```

Or using system Maven:
```bash
mvn clean install
```

### 3. Run Tests

```bash
./mvnw test
```

### 4. Start the Backend

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

### 5. Start the Frontend

Open `src/main/frontend/index.html` in your browser, or use a local development server:

```bash
# Using Python
cd src/main/frontend
python -m http.server 5500

# Using Node.js http-server
npm install -g http-server
cd src/main/frontend
http-server -p 5500

# Using VS Code Live Server extension
# Right-click on index.html and select "Open with Live Server"
```

The frontend will be available at `http://localhost:5500`

## API Documentation

### Base URL
```
http://localhost:8080/api/todos
```

### Endpoints

#### Get All Tasks
```http
GET /api/todos
```

**Response:**
```json
[
  {
    "id": 1,
    "title": "Learn Spring Boot",
    "done": false
  }
]
```

#### Create Task
```http
POST /api/todos
Content-Type: application/json

{
  "title": "New task"
}
```

**Validation:**
- Title must be at least 3 characters
- Title cannot be null or empty

**Success Response:**
```json
{
  "id": 1,
  "title": "New task",
  "done": false
}
```

**Error Response:**
```json
{
  "error": "Title must be at least 3 characters"
}
```

#### Toggle Task Status
```http
PUT /api/todos/{id}/toggle
```

**Success Response:**
```json
{
  "id": 1,
  "title": "Learn Spring Boot",
  "done": true
}
```

**Error Response (404):**
```json
{
  "error": "Not found"
}
```

#### Delete Task
```http
DELETE /api/todos/{id}
```

**Success Response:** `200 OK`

**Error Response (404):**
```json
{
  "error": "Not found"
}
```

## Testing

The project includes comprehensive unit tests for the service layer:

```bash
# Run all tests
./mvnw test

# Run tests with coverage
./mvnw test jacoco:report

# Run specific test class
./mvnw test -Dtest=TodoServiceTest
```

### Test Coverage

- ✅ Create task with valid title
- ✅ Create task with short title (validation)
- ✅ Create task with empty title (validation)
- ✅ Toggle existing task
- ✅ Toggle non-existing task
- ✅ Delete existing task
- ✅ Delete non-existing task

## Configuration

### CORS Configuration

The application is configured to accept requests from:
- `http://localhost:5500`
- `http://127.0.0.1:5500`

To add more origins, edit `src/main/java/com/example/TinyDesk/config/CorsConfig.java`:

```java
registry.addMapping("/api/**")
    .allowedOrigins("http://localhost:5500", "http://your-domain.com")
    .allowedMethods("GET", "POST", "PUT", "DELETE");
```

### Port Configuration

To change the backend port, add to `application.properties`:

```properties
server.port=8081
```

## Development

### Adding New Features

1. **Create the model** in `model/` package
2. **Add repository methods** in `repository/` package
3. **Implement business logic** in `service/` package
4. **Create REST endpoints** in `controller/` package
5. **Write unit tests** in `src/test/java/`
6. **Update frontend** in `src/main/frontend/`

### Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Write self-documenting code
- Add unit tests for new functionality
- Keep methods small and focused

## Production Considerations

This application uses in-memory storage, which means:
- ⚠️ **Data is lost on restart**
- ⚠️ **Not suitable for production use**

For production, consider:
- Adding database persistence (PostgreSQL, MySQL, H2)
- Implementing data validation
- Adding authentication/authorization
- Setting up proper logging
- Implementing error handling
- Adding API rate limiting
- Using environment variables for configuration

## Troubleshooting

### Port Already in Use
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>
```

### CORS Errors
- Ensure the frontend is running on `http://localhost:5500`
- Check `CorsConfig.java` for allowed origins
- Clear browser cache and try again

### Tests Failing
```bash
# Clean and rebuild
./mvnw clean test

# Check Java version
java -version  # Should be 21+
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Author

Built with ☕ using Spring Boot

## Acknowledgments

- Spring Boot team for the excellent framework
- Bootstrap for the UI components
- GitHub for design inspiration