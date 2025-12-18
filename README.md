<h1> Person API </h1>

<h2> Status: Work in Progress </h2> 

A RESTful API for Person management developed as a practice project to master modern Spring Boot features, RESTful principles, and industry best practices.

Features (Implemented)
- RESTful endpoints for Person CRUD
- DTO pattern implementation
- Swagger/OpenAPI 3 documentation
- HATEOAS support
- Content Negotiation (JSON/XML/YAML)
- Database integration with MySQL
- Integration tests with Testcontainers
- Containerized MySQL testing environment
- CORS configuration with origin patterns
- Dynamic port allocation for parallel testing
- Test isolation with independent test cases

Testing Strategy
- Unit Tests: JUnit 5, Mockito
- Integration Tests: Testcontainers, MySQL Docker containers
- API Tests: RestAssured with detailed logging
- Database Tests: Isolated MySQL containers per test suite
- CORS Tests: Origin validation with positive/negative scenarios
- Port Management: Dynamic port allocation to avoid conflicts

Tech Stack
- Java 17+
- Spring Boot 3.4.0
- Spring Data JPA
- Spring HATEOAS
- OpenAPI 3 (SpringDoc OpenAPI)
- Maven
- Testcontainers 1.20.4 (integration testing)
- MySQL 9.1.0 (test environment via Docker)
- Flyway (database migrations)
- RestAssured (API testing)

Links
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- API Docs (JSON): http://localhost:8080/v3/api-docs
- API Docs (YAML): http://localhost:8080/v3/api-docs.yaml

Getting Started
- Prerequisites
- Java 17 or higher
- Maven 3.8+
- Docker (required for integration tests)
- Docker Desktop or Docker Engine running

<pre><code>Clone the repository
git clone https://github.com/tiagoribeine/person-api.git
cd person-api 

Build the project (including tests)
mvn clean install

Run the application
mvn spring-boot:run
</code></pre>

<h2> CORS Configuration </h2>
The API supports CORS with configurable origin patterns:
<pre><code>cors.originPatterns=http://localhost:3000,http://localhost:8080,https://www.erudio.com.br</code></pre>

<h2>Test Environment</h2>
- MySQL 9.1.0 via Docker Testcontainers
- Automatic port allocation for parallel test execution
- Database migrations via Flyway in test containers
- Isolated database per test run

<h2>Test Structure</h2>
integrationtests/<br>
├── controllers/     # API endpoint tests <br>
├── dto/            # Data transfer object tests <br>
├── swagger/        # OpenAPI documentation tests <br>
└── testcontainers/ # Testcontainers base configuration <br>

<h2>Project Structure</h2>
src/ <br>
├── main/ <br>
│   ├── java/github/com/tiagoribeine/ <br>
│   │   ├── config/          # Configuration classes <br>
│   │   ├── controller/      # REST controllers <br>
│   │   ├── model/          # Entity models <br> 
│   │   ├── repository/     # Data repositories <br>
│   │   ├── service/        # Business logic <br>
│   │   └── dto/           # Data transfer objects <br>
│   └── resources/ <br>
│       ├── db/migration/   # Flyway migrations <br>
│       └── application.properties <br>
└── test/ <br>
└── java/github/com/tiagoribeine/ <br> 
└── integrationtests/ # Integration tests<br>

<h2>License</h2>
This project is licensed under the MIT License - see the LICENSE file for details.