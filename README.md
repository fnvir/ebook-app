# Ebook Application

A Spring Boot application for sharing pdfs. It includes features for user authentication, file storage, and more. The application is configured to use PostgreSQL as the database and supports Docker for containerization.

## Features

- **User Authentication**: Register and login users with JWT-based authentication.
- **File Storage**: Upload and manage files with validation for file types and size.
- **Swagger UI**: API documentation and testing using Swagger UI.
- **Docker Support**: Containerize the application using Docker.

## Prerequisites

- Java 21+
- PostgreSQL

  **OR**

- Docker

## Configuration

The application uses environment variables for configuration. Create a `.env` file in the root directory similar to the `.env.example` file.

## Running the Application

### Using Docker Compose

1. **Build and Run the Docker image**:
    ```sh
    docker compose up
    ```

### Using Gradle
1. **Build & Run the project**:
    ```sh
    ./gradlew clean bootRun
    ```
    Make sure to run PostgreSQL and create a database with a name similar to `DB_NAME` specified in the `.env` file before running the application.

## Swagger UI

The application includes Swagger UI for API documentation and testing. Once the application is running, you can access Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

To access the OpenAPI docs:
```
http://localhost:8080/api-docs

http://localhost:8080/api-docs.yaml
```
## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Contact

For any inquiries or issues, please contact the project maintainer.
