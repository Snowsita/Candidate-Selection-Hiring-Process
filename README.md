
# Candidate Selection

## Project Overview

This project is a candidate selection system composed of a Spring Boot backend application and a MySQL database. The system is designed to manage client information, including personal details and statistics. The entire environment is containerized using Docker, making it easy to set up and run.

### Key Technologies

*   **Backend:** Java, Spring Boot, Spring Data JPA, Spring Security
*   **Database:** MySQL
*   **Build Tool:** Maven
*   **Containerization:** Docker, Docker Compose
*   **Database Migrations:** Flyway
*   **API Documentation:** OpenAPI (Swagger)

### Architecture

The system follows a microservices architecture, with the following services defined in the `docker-compose.yml` file:

*   `customer-service`: The main backend application that provides a REST API for managing clients.
*   `mysql-db`: The MySQL database that stores the client data.
*   `adminer`: A web-based database management tool.

## Building and Running

### Prerequisites

*   Docker
*   Docker Compose
*   Java 17
*   Maven

### Running the Application

1.  **Build the `customer-service` application:**

    ```bash
    cd services/customer-service
    mvn clean install
    ```

2.  **Run the application using Docker Compose:**

    From the root directory of the project, run the following command:

    ```bash
    docker-compose up -d
    ```

    This will start all the services in detached mode.

### Accessing the Application

*   **API:** The `customer-service` API is available at `http://localhost:8080`.
*   **API Documentation:** The OpenAPI (Swagger) documentation is available at `http://localhost:8080/swagger-ui.html`.
*   **Database Admin:** The Adminer web interface is available at `http://localhost:8081`. You can use the database credentials from the `docker-compose.yml` file to log in.

## Endpoints

The following endpoints are available in the `customer-service` API:

*   **Create a new client:**
    *   **Method:** `POST`
    *   **URL:** `/api/v1/clients`
    *   **Body:**
        ```json
        {
          "firstName": "string",
          "lastName": "string",
          "age": 0,
          "dateOfBirth": "2025-09-21"
        }
        ```
*   **Get client statistics:**
    *   **Method:** `GET`
    *   **URL:** `/api/v1/clients/stats`
*   **Get all clients:**
    *   **Method:** `GET`
    *   **URL:** `/api/v1/clients`

## Postman Collection

A Postman collection is available in the `postman` directory. You can import the `postman_collection.json` file into Postman to test the API endpoints.

## Development Conventions

### API

The REST API follows the conventions of Spring Web and is documented using OpenAPI. The base path for all the endpoints is `/api/v1`.

### Database

Database migrations are managed using Flyway. To create a new migration, add a new SQL file to the `src/main/resources/db/migration` directory with the following naming convention: `V<VERSION>__<DESCRIPTION>.sql`.

### Testing

The project includes integration tests that use Testcontainers to spin up a MySQL container for testing purposes. You can run the tests using the following command:

```bash
cd services/customer-service
mvn test
```
