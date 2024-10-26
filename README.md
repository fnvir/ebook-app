# Ebook Application[![Build Status](https://github.com/fnvir/ebook-app/actions/workflows/gradle.yml/badge.svg)](https://github.com/fnvir/ebook-app/actions/workflows/gradle.yml)

A Spring Boot application for uploading & sharing books. It includes features like user authentication and authorization, file storage, and more. The application is configured to use PostgreSQL as the database and supports Docker for containerization. API descriptions are also avaiable with OpenAPI and Swagger UI.


## Features

**User Authentication & Authorization**: 
  - Register and login users with JWT authentication.
  - Secure endpoints with Spring Security.
  - Role-based access control.

**Book Management**:
  - Read, add, or delete books.
  - View list of all uploaded books.
  - View book details.

**Reviews & Favourites**: 
  - Add reviews to uploaded books.
  - Add books to their favourites.
  - Retrieve reviews by user or book.
  - Paginated review retrieval.

**File Storage**:
  - Upload and manage PDFs & other files.
  - Validate file types and size.
  - Store user-specific content.
  - Serve files as resources.

**Pagination & Sorting**: Support for paginated API responses.

**Swagger UI**: API documentation and testing using Swagger UI.

**Docker Support**: Containerize the application using Docker.


## Prerequisites

- Java 21+
- PostgreSQL

  **OR**

- Docker


## Configuration

The application uses environment variables for configuration. Create a `.env` file in the root directory similar to the [.env.example](./.env.example) file.


## Running the Application

### Using Docker Compose:
```sh
docker compose up
```

### Using Gradle:
```sh
./gradlew clean bootRun
```

*Make sure to run PostgreSQL and create a database with a name similar to `DB_NAME` specified in the `.env` file before running the application. (Not needed in Docker)*

## ER Diagram

![ebook_app - public](https://github.com/user-attachments/assets/a8315b88-567a-46ac-8980-3fa326260831)


## Swagger UI

The app includes Swagger UI for API documentation and testing. Once the app is running, you can access Swagger UI at:

http://localhost:8080/swagger-ui/index.html

To access the OpenAPI docs:

http://localhost:8080/api-docs or http://localhost:8080/api-docs.yaml

![Swagger-UI-10-26-2024_07_35_PM](https://github.com/user-attachments/assets/b63287bc-1942-4de8-ad5e-cc1035acf1f7)




## Contact

For any inquiries or issues, please contact the project maintainer.
