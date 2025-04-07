# Game Manager Microservice

The Game Manager microservice is part of the Tracktainment application, which is designed to track books, movies, and games consumed by users. This microservice is responsible for managing games and their associated metadata. It integrates with the Dux Manager microservice to manage digital user assets.

## Overview

The Game Manager microservice provides CRUD (Create, Read, Update, Delete) operations for managing games. It adheres to the principles of Clean Architecture , ensuring modularity, scalability, and maintainability. The service interacts with a PostgreSQL database for persistent storage and communicates with the Dux Manager microservice via REST APIs to manage digital user assets.

## Architecture

The project follows a clean architecture with clear separation of concerns:

- **Application Module**: Handles application configuration and properties.
- **Core Module**: Contains domain models, DTOs, use cases, and interfaces for data providers
- **Data Provider SQL Module**: Implementation of persistence layer using JPA/Hibernate
- **Entry Point REST Module**: REST API controllers and exception handling
- **Data Provider REST Module**: Integration with external DuxManager service

## Features

- Complete CRUD operations for game entities
- Advanced filtering and search capabilities
- Sorting by various game attributes
- Integration with DuxManager for asset tracking
- Comprehensive validation and error handling

## API Endpoints

| Method |       Endpoint       |       Description       |
|--------|----------------------|-------------------------|
| POST   | `/api/v1/games`      | Create a new game       |
| GET    | `/api/v1/games/{id}` | Get a game by ID        |
| GET    | `/api/v1/games`      | List games with filters |
| PATCH  | `/api/v1/games/{id}` | Update a game           |
| DELETE | `/api/v1/games/{id}` | Delete a game           |

## Data Model

The Game entity has the following attributes:

- `id`: Unique identifier
- `title`: Game title
- `platform`: Game platform
- `genre`: Game genre
- `developer`: Game developer
- `releaseDate`: Date of release
- `createdAt`: Record creation timestamp
- `updatedAt`: Last update timestamp

## Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL or another compatible database
- Access to DuxManager service
- Docker (optional, for containerized deployment)

### Configuration

Create an `application.properties` or `application.yml` file with the following properties:

```properties
# Database configuration
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/game-manager
    username: postgres
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update

# DuxManager service URL
http:
  url:
    dux-manager: http://localhost:8081/dux-manager/api/v1
```

### Building

To build the application, run the following command:
```bash
mvn clean package
```

### Running

To run the application locally, use the following command:
```bash
java -jar game-manager.jar
```

### Docker Setup

The game-manager application can now be containerized using Docker. To run the application in Docker, follow these steps:
 - Step 1: Build the Docker Image - 
Run the following command to build the Docker image:
```
docker-compose up --build
```

 - Step 2: Configure the DuxManager Service - 
If the dux-manager service is running on your host machine (outside Docker), update the http.url.dux-manager property in the application.yml file to use host.docker.internal (for Windows/macOS) or the host machine's IP address (for Linux):
```
http:
  url:
    dux-manager: http://host.docker.internal:8081/dux-manager/api/v1
```

Alternatively, if you Dockerize the dux-manager service as well, ensure both services are part of the same Docker network and use the service name (dux-manager) as the hostname:
```
http:
  url:
    dux-manager: http://dux-manager:8081/dux-manager/api/v1
```

 - Step 3: Start the Containers - 
Start the containers using the following command:
```
docker-compose up
```

The game-manager service will be accessible at http://localhost:8080, and it will communicate with the dux-manager service as configured.

## Error Handling

The service provides structured error responses with the following format:

```json
{
  "code": "E-002",
  "httpStatusCode": 404,
  "reason": "Resource not found.",
  "message": "Game your-game-id not found."
}
```

Common error codes:
- `E-001`: Internal server error
- `E-002`: Resource not found
- `E-003`: Resource already exists
- `E-007`: Parameter validation error

## Integration with DuxManager

The Game Manager service integrates with DuxManager for asset tracking. Each game created in the system is also registered as an asset in DuxManager with the following attributes:
- `externalId`: Game ID
- `type`: "game"
- `permissionPolicy`: "owner"
- `artifactInformation`: Contains group, artifact, and version details

## Validation

The service includes comprehensive validation for all inputs:
- Game title, platform, genre and developer validation
- Date format validation
- Query parameter validation

## Development

### Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Jakarta Validation
- Feign Client
- Lombok
- MapStruct
- Logging with SLF4J
- OkHttp
- PostgreSQL
- Maven
- Docker

### Project Structure

```
com.tracktainment.gamemanager
├── api                    # API interfaces
├── client                 # External service clients
├── config                 # Configuration classes
├── controller             # REST controllers
├── dataprovider           # Data provider implementations
├── domain                 # Domain models
├── dto                    # Data Transfer Objects
├── entity                 # JPA entities
├── exception              # Exception handling
├── mapper                 # Object mappers
├── repository             # Data repositories
├── security               # Security context
├── usecases               # Business logic implementation
└── util                   # Utility classes
```

### Next Features

- Authentication and authorization;
- Unit testing;
- Update protocol from HTTP to HTTPS;
- Database encryption;
- CI/CD pipeline.

## Potential Tracktainment Upgrades

- **Review Microservice**: A microservice to handle reviews of books, games and movies.
- **Recommendation Microservice**: A microservice to handle books, games and movies recommendations based on what the user has consumed so far.
