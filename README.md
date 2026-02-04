This project was developed as a **hands-on study project** focused on mastering **Spring Boot**, **Spring Security**, and modern REST API best practices, while maintaining production-level code quality.

<h1> Person API </h1>

<h2> Status: Work in Progress </h2> 

A RESTful API for Person management developed as a practice project to master modern Spring Boot features, RESTful principles, and industry best practices.

<h2>Features (Implemented)</h2>

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
- Pagination with sorting and filtering capabilities
- Advanced query methods with Spring Data JPA specifications
- JWT-based authentication (Access & Refresh Tokens)

<h3>Automatic Setup </h3>

The application is configured to **automatically create everything** on first run:

**Database** → Created automatically (`createDatabaseIfNotExist=true`)  
**Tables** → Created via Flyway migrations  
**Schema History** → Tracked in `flyway_schema_history`

<h3>Set Environment Variables </h3>

DB_USERNAME= `your_db_username`
DB_PASSWORD= `your_db_password`
SECRET_KEY= `your-256-bit-secret-key-here-minimum-256-bits`
EMAIL_USERNAME= `your-email@gmail.com`   # Optional - for email features
EMAIL_PASSWORD= `your-google-api-key`    # Optional - for email features

<h2> Authentication</h2>

This API uses <strong>JWT (JSON Web Token)</strong> for stateless authentication.

To access protected endpoints, clients must authenticate first and obtain an access token.

<h3>Sign In</h3>

Authenticates a user and returns an access token and a refresh token.

<strong>Endpoint</strong>
<pre><code>POST /auth/signin </code></pre> 

<strong>Request Body</strong>

<h1>Default Test User</h1>
<strong>These are TEST credentials ONLY - Do not use or keep this info in production:</strong>

<pre><code>{
  "username": "leandro",
  "password": "admin123"
}</code></pre>

<strong>Response – 200 OK</strong>
<pre><code>{
  "headers": {},
  "body": {
    "username": "leandro",
    "authenticated": true,
    "created": "2026-01-28T23:40:12.000+00:00",
    "expiration": "2026-01-29T00:40:12.000+00:00",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
  },
  "statusCode": "OK",
  "statusCodeValue": 200
}</code></pre>

<h3>Using the Access Token</h3>

For protected endpoints, include the access token in the <code>Authorization</code> header:

<pre><code>Authorization: Bearer &lt;accessToken&gt;</code></pre>

<h3>Notes</h3>

- The API is stateless and does not use HTTP sessions.
- The access token has a limited lifetime.
- The refresh token can be used to obtain a new access token when the current one expires.

<h2>File Upload/Download Features</h2>

<h3>Upload Features</h3>

- Single file upload endpoint (`POST /api/file/v1/uploadFile`)
- Multiple files upload endpoint (`POST /api/file/v1/uploadMultipleFiles`)
- Configurable upload directory via application properties
- File type and size validation
- Automatic file name generation to prevent conflicts
- Returns download URL for uploaded files

<h3>Download Features</h3>

- Secure file download endpoint (`GET /api/file/v1/downloadFile/{fileName}`)
- Path traversal protection
- File existence validation
- Content-Type header auto-detection
- Content-Disposition header for browser downloads

<h2>Configuration</h2>

Configure upload directory in <code>application.properties</code>:

<pre><code>file.upload-dir=/path/to/upload/directory</code></pre>

<h2>Testing Strategy</h2>

- Unit Tests: JUnit 5, Mockito
- Integration Tests: Testcontainers, MySQL Docker containers
- API Tests: RestAssured with detailed logging
- Database Tests: Isolated MySQL containers per test suite
- CORS Tests: Origin validation with positive/negative scenarios
- Port Management: Dynamic port allocation to avoid conflicts

<h2>Tech Stack</h2>

- Java 17+
- Spring Boot 3.4.0
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- Spring HATEoAS
- OpenAPI 3 (SpringDoc OpenAPI)
- Maven
- Testcontainers 1.20.4
- MySQL 9.1.0 (test environment via Docker)
- Flyway
- RestAssured

<h2>API Endpoints</h2>

<strong>Authentication</strong>
- `POST /auth/signin` – Authenticate user and generate JWT tokens

<strong>Person</strong>
- `GET /api/person/v1`
- `GET /api/person/v1/{id}`
- `POST /api/person/v1`
- `PUT /api/person/v1`
- `PATCH /api/person/v1/{id}`
- `DELETE /api/person/v1/{id}`
- `GET /api/person/v1/findPeopleByName/{firstName}`

<h2>Links</h2>

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- API Docs (JSON): http://localhost:8080/v3/api-docs
- API Docs (YAML): http://localhost:8080/v3/api-docs.yaml

<h2>Getting Started</h2>

<h3>Prerequisites</h3>

- Java 17 or higher
- Maven 3.8+
- Docker (required for integration tests)
- MySQL Database

<h3>Step 1: Clone the Repository</h3>
<pre><code>git clone https://github.com/tiagoribeine/person-api.git
cd person-api</code></pre>

<h3>Step 2: Start MySQL Database</h3>
<strong>Option A: Using Docker (Recommended)</strong>
<pre><code># Start MySQL container
docker run -d \
  --name person-mysql \
  -e MYSQL_ROOT_PASSWORD=admin123 \
  -p 3306:3306 \
  mysql:8.0

# Wait for MySQL to initialize
sleep 10</code></pre>

<h3>Step 3: Build the Project</h3>
<pre><code>mvn clean install</code></pre>

<h3>Step 4: Run the Application</h3>
<pre><code>mvn spring-boot:run</code></pre>

<h2>CORS Configuration</h2>

The API supports CORS with configurable origin patterns:

<pre><code>cors.originPatterns=http://localhost:3000,http://localhost:8080,https://www.erudio.com.br</code></pre>

<h2>Test Environment</h2>

- MySQL 9.1.0 via Docker Testcontainers
- Automatic port allocation for parallel test execution
- Database migrations via Flyway
- Isolated database per test run

<h2>Test Structure</h2>

integrationtests/<br>
├── controllers/<br>
├── dto/<br>
├── swagger/<br>
└── testcontainers/<br>

<h2>Project Structure</h2>

src/<br>
├── main/<br>
│   ├── java/github/com/tiagoribeine/<br>
│   │   ├── config/<br>
│   │   ├── controller/<br>
│   │   ├── model/<br>
│   │   ├── repository/<br>
│   │   ├── service/<br>
│   │   └── dto/<br>
│   └── resources/<br>
│       ├── db/migration/<br>
│       └── application.properties<br>
└── test/<br>
└── integrationtests/<br>

<h2>License</h2>

This project is licensed under the MIT License.