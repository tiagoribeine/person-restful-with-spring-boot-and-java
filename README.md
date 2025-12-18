# Person API

**Status: Work in Progress**

A RESTful API for Person management with modern Spring Boot features.

## 🚀 Features (Implemented)
- RESTful endpoints for Person CRUD
- DTO pattern implementation
- Swagger/OpenAPI 3 documentation
- HATEOAS support
- Content Negotiation (JSON/XML)
- Database integration
- **Integration tests with Testcontainers**
- **Containerized MySQL testing environment**

## 🧪 Testing Strategy
- **Unit Tests**: JUnit 5, Mockito
- **Integration Tests**: Testcontainers, MySQL Docker
- **API Tests**: RestAssured, Swagger validation
- **Database Tests**: Isolated MySQL containers

## Tech Stack
- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Spring HATEOAS
- OpenAPI 3 (Swagger)
- Maven
- H2 Database (development)
- **Testcontainers** (integration testing)
- **MySQL 8.0.32** (test environment)

## Links
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- API Docs (JSON): http://localhost:8080/v3/api-docs

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- **Docker** (required for integration tests)

### Installation
```bash
# Clone the repository
git clone https://github.com/tiagoribeine/person-api.git

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run