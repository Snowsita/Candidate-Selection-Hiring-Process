# Candidate Selection Project

## Project Overview

This project is a candidate selection system composed of a Spring Boot backend application and a MySQL database. The system is designed to manage client information, including personal details and statistics. The entire environment is containerized using Docker, making it easy to set up and run.

### Key Technologies

* **Backend:** Java 17, Spring Boot, Spring Data JPA, Spring Security
* **Database:** MySQL 8.0
* **Build Tool:** Maven
* **Containerization:** Docker, Docker Compose
* **Database Migrations:** Flyway
* **API Documentation:** Springdoc OpenAPI (Swagger)

### Architecture

The system follows a microservices architecture, with the following services defined in the `docker-compose.yml` file:

* `customer-service`: The main backend application that provides a REST API for managing clients.
* `mysql-db`: The MySQL database that stores the client data.
* `adminer`: A web-based database management tool.

## Building and Running the Application

### Prerequisites

* Docker & Docker Compose
* Java 17 (for local development outside Docker)
* Maven (for local development outside Docker)

### Running with Docker Compose

From the root directory of the project, run the following command:

```bash
docker-compose up --build -d
```

This single command will:

1.  Build the `customer-service` Docker image from its `Dockerfile`.
2.  Start the `customer-service`, `mysql-db`, and `adminer` containers in detached mode.

To stop all the services, run:

```bash
docker-compose down
```

### Accessing the Application

* **API:** The `customer-service` API is available at `http://localhost:8080`.
* **API Documentation:** The OpenAPI (Swagger) documentation is available at `http://localhost:8080/swagger-ui.html`.
* **Database Admin:** The Adminer web interface is available at `http://localhost:8081`. You can use the database credentials from the `docker-compose.yml` file to log in (Server: `mysql-db`, User: `user`, Password: `password`).

### Security

All API endpoints under `/api/v1/` are secured using **HTTP Basic Authentication**. To access them, you must provide the following credentials:

* **Username:** `user`
* **Password:** `password`

The API documentation endpoints (Swagger UI) are publicly accessible without authentication. The provided Postman collection is pre-configured with these credentials.

## API Endpoints

The base path for all the endpoints is `/api/v1`.

* **Create a new client:**

    * **Method:** `POST /api/v1/clients`
    * **Authentication:** Required.
    * **Success Response:** `201 Created`
    * **Body:**
      ```json
      {
        "firstName": "Juan",
        "lastName": "Perez",
        "age": 30,
        "dateOfBirth": "1994-08-15"
      }
      ```

* **Get client statistics:**

    * **Method:** `GET /api/v1/clients/stats`
    * **Authentication:** Required.
    * **Success Response:** `200 OK`

* **Get all clients:**

    * **Method:** `GET /api/v1/clients`
    * **Authentication:** Required.
    * **Success Response:** `200 OK`

## Postman Collection

A Postman collection is available at the root of the project. You can import the `postman_collection.json` file into Postman to test the API endpoints.

## Development Conventions

### Database

Database migrations are managed using Flyway. To create a new migration, add a new SQL file to the `src/main/resources/db/migration` directory with the following naming convention: `V<VERSION>__<DESCRIPTION>.sql`.

### Testing

The project includes a comprehensive test suite:

* **Unit Tests:** Service layer logic is tested in isolation using Mockito.
* **Integration Tests:** The full application stack is tested using Testcontainers, which spins up a real MySQL container for each test run.

You can run all tests using the following command from the `services/customer-service` directory:

```bash
mvn test
```