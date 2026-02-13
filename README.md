# Person API - Spring Boot 3 & Dockerized Environment

[![CI/CD Status](https://github.com/tiagoribeine/new-rest-with-spring-boot-and-java-erudio/actions/workflows/continuous-deployment.yml/badge.svg)](https://github.com/tiagoribeine/new-rest-with-spring-boot-and-java-erudio/actions)

This project was developed as a **hands-on study project** focused on mastering **Spring Boot 3**, **Spring Security**, and modern REST API best practices, while maintaining production-level code quality.

> **Status:** Work in Progress 🛠️

## 🚀 Features (Implemented)

- **RESTful CRUD**: Full management for Person entities.
- **DTO Pattern**: Clean separation between data transfer and persistence.
- **Swagger/OpenAPI 3**: Interactive documentation.
- **HATEOAS**: Implementation of hypermedia links for API navigability.
- **Content Negotiation**: Support for JSON, XML, and YAML.
- **Flyway Migrations**: Automated database versioning.
- **Security**: JWT-based authentication (Access & Refresh Tokens).
- **File Management**: Secure Upload/Download features.
- **Advanced Search**: Pagination, sorting, and filtering with Spring Data JPA.
- **CORS**: Configurable origin patterns for cross-origin requests.

## 🛠️ Tech Stack

- **Java 21** (Running on Docker)
- **Spring Boot 3.4.1**
- **MySQL 9.1.0**
- **Docker & Docker Compose**
- **Flyway** (Migrations)
- **Spring Security & JWT**
- **Testcontainers** (Integration Tests)
- **RestAssured** (API Testing)
- **GitHub Actions** (CI/CD)

---

## 🐳 Quick Start (Dockerized)

The easiest way to run the entire stack (API + Database + Management UI) is using Docker Compose.

### 1. Prerequisites
- Docker & Docker Desktop installed.
- A `.env` file in the project root (see template below).

### 2. Set Environment Variables (`.env`)
Create a `.env` file with these keys:
```env
# Database Settings
MYSQL_ROOT_PASSWORD=admin123
MYSQL_DATABASE=rest_with_spring_boot_erudio

# Spring Boot Settings
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=admin123
SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/rest_with_spring_boot_erudio?useSSL=false&allowPublicKeyRetrieval=true&autoReconnect=true

# Security & Others
SECRET_KEY=your-256-bit-secret-key-minimum
PORTAINER_PASSWORD=admin123456789
CORS_ORIGINAL_PATTERNS=http://localhost:8080,http://localhost:3000
```

Spin up the Enviroment:
```
docker compose up -d
```

📖 Documentation & Management
Once the containers are running:

- Swagger UI: http://localhost/swagger-ui/index.html
- API Docs (JSON): http://localhost/v3/api-docs
- Portainer (Docker UI): http://localhost:9000
- MySQL External Access: localhost:3308 (User: root)

🔐 Authentication
This API uses JWT (JSON Web Token) for stateless authentication.

Sign In
Endpoint: POST /auth/signin
Default Test User:

```
{
    "username": "leandro", 
    "password": "admin123"
}
```

<h3> Using the Token </h3>

Include the token in the header of protected requests:
```
Authorization: Bearer <your_access_token>
```

<h3> File Upload/Download </h3>

- Upload Single: POST /api/file/v1/uploadFile
- Upload Multiple: POST /api/file/v1/uploadMultipleFiles
- Download: GET /api/file/v1/downloadFile/{fileName}

<h3> Testing Strategy </h3>

- Unit Tests: JUnit 5 and Mockito.
- Integration Tests: RestAssured + Testcontainers (spins up a real MySQL 9.1.0 container for tests).
- Isolation: Each test suite runs in an isolated environment with dynamic port allocation.

To run tests locally:
```
mvn clean verify
```

<h3> DevOps & Automation (CI/CD)</h3>
Every push to the main branch triggers the GitHub Actions pipeline:

- Verify: Runs Maven build and all integration tests.
- Dockerize: Builds a production-ready image.
- Push: Pushes to Docker Hub as tiagoribeine/new-rest-with-spring-boot-and-java-erudio.

[View Image on Docker Hub](https://hub.docker.com/r/tiagoribeine/new-rest-with-spring-boot-and-java-erudio)

<h3>License </h3>
This project is licensed under the MIT License.



