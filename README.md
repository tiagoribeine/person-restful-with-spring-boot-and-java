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

## Tech Stack
- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Spring HATEOAS
- OpenAPI 3 (Swagger)
- Maven
- H2 Database (development)

## 🛠️ Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- Docker (optional)

### Installation
```bash
# Clone the repository
git clone https://github.com/tiagoribeine/person-api.git

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run