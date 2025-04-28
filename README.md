# Game Manager Microservice
The Game Manager microservice is part of the Tracktainment application, which is designed to track books, movies, and games consumed by users.  
This microservice is responsible for managing games and their associated metadata. It integrates with the Dux Manager microservice to manage digital user assets.

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [API Documentation](#api-documentation)
- [Setup and Installation](#setup-and-installation)
  - [Prerequisites](#prerequisites)
  - [Local Development](#local-development)
  - [Docker Setup](#docker-setup)
- [Authentication](#authentication)
- [Error Handling](#error-handling)
- [Validation](#validation)
- [Logging](#logging)
- [Integration with DuxManager](#integration-with-duxmanager)
- [Next Features](#next-features)
- [Potential Tracktainment Upgrades](#potential-tracktainment-upgrades)

## Overview
Game Manager is a microservice application that provides RESTful APIs for managing games. It follows clean architecture principles with a clear separation of concerns, making it maintainable, testable, and scalable.  
The application allows users to create, read, update, and delete games, while also integrating with an external system (DUX Manager) for asset management.

## Architecture
The project follows a clean architecture with clear separation of concerns:
- **Application Module**: Handles application configuration and properties
- **Core Module**: Contains business rules, domain models, and use cases
- **Data Provider SQL Module**: Implementation of persistence layer using JPA/Hibernate
- **Data Provider REST Module**: Integration with external (Dux Manager) service
- **Entry Point REST Module**: REST API controllers and resources

## Features
- Complete CRUD operations for entities;
- Advanced filtering and search capabilities;
- Sorting by various attributes;
- Feign Client Integration with DuxManager for asset tracking;
- Comprehensive validation and error handling;
- Design Patterns integration (DTO, Builder, Factory and Code Generation);
- Docker containerization for deployment;
- OAuth2/JWT authentication;
- Swagger documentation;
- Comprehensive unit testing with JUnit & Mockito with over 80% line coverage;
- Code analysis performed against SonarQube using Jacoco;
- HTTP requests/responses logging;
- Traceability, Observability and OpenTelemetry integration using provided logs.

## Tech Stack
- Java 17
- Spring Boot 3.3.4
- Spring Data JPA
- Spring Security with OAuth2
- Jakarta Validation
- Feign Client
- Lombok
- MapStruct
- PostgreSQL
- Maven
- Docker
- Swagger/OpenAPI
- HTTPS enabled via SSL certificates
- HTTP request/response logging 
- JUnit 5 & Mockito
- SonarQube & Jacoco

### Project Structure
```
game-manager
├── game-manager-application           # Spring Boot application module│
├── game-manager-core                  # Core domain and business logic
│       ├── domain                     # Domain models
│       ├── dto                        # Data Transfer Objects
│       ├── exception                  # Exception definitions
│       ├── mapper                     # Mappers for core module
│       ├── security                   # Security context
│       ├── usecases                   # Business use cases
│       ├── util                       # Utility classes
│       └── dataprovider               # Data provider interfaces
│
├── game-manager-entrypoint-rest       # REST API entry point
│       ├── api                        # API interfaces
│       ├── controller                 # REST controllers
│       ├── exception                  # REST exception handlers
│       ├── mapper                     # Mappers for REST module
│       ├── security                   # Security configuration
│       └── swagger                    # OpenAPI/Swagger config
│
├── game-manager-dataprovider-sql      # SQL data provider implementation
│       ├── dataprovider               # SQL data provider implementations
│       ├── entity                     # JPA entities
│       ├── mapper                     # SQL-specific mappers
│       └── repository                 # Spring Data repositories
│
├── game-manager-dataprovider-rest     # REST client data provider
│       ├── client                     # External service clients
│       ├── config                     # REST client configuration
│       └── dataprovider               # REST data provider implementations
│
└── resources                          # Project resources
    ├── certificate                    # SSL certificates
    └── docker                         # Docker configuration
```

## API Endpoints
| Method |       Endpoint       |       Description       |
|--------|----------------------|-------------------------|
| POST   | `/api/v1/games`      | Create a new game       |
| GET    | `/api/v1/games/{id}` | Get a game by ID        |
| GET    | `/api/v1/games`      | List games with filters |
| PATCH  | `/api/v1/games/{id}` | Update a game           |
| DELETE | `/api/v1/games/{id}` | Delete a game           |

## API Documentation
When running the application, the Swagger UI is available at:
```
https://localhost:8445/game-manager/swagger-ui.html
```
The OpenAPI specification is available at:
```
https://localhost:8445/game-manager/api-docs
```

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

## Setup and Installation
### Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL 15+
- DuxManager service running (https://github.com/FPetronilho/dux-manager)
- Docker (optional, for containerized deployment)

### Local Development
 - Step 1 - Clone the repository
```
git clone https://github.com/FPetronilho/game-manager.git
cd game-manager
```
- Step 2 - Set up the PostgreSQL database:  
Create a database named 'game-manager'
- Step 3 - Configure application properties:  
Create a .env file to setup environment variables or update game-manager-application/src/main/resources/application-local.yaml.  
Ensure that the http.url.dux-manager property in the application.yaml file points to the correct URL:
```
http:
  url:
    dux-manager: https://localhost:8443/dux-manager/api/v1
```
- Step 4 - Build the project
```
mvn clean install
```
- Step 5 - Run the application
```bash
java -jar game-manager.jar
```

### Docker Setup
- Step 1 - Create a docker network:  
As Game Manager depends that Dux Manager is up and running, create a docker network so that both microservices can communicate.
```
docker network create your-network
```
- Step 2 - Set environment variables in .env file:  
Ensure that the http.url.dux-manager property in the application.yaml file points to the correct URL:
```
http:
 url:
   dux-manager: https://dux-manager:8443/dux-manager/api/v1
```
- Step 3 - Build and run with Docker compose
```
cd resources/docker
docker-compose up -d
``` 
The game-manager service will be accessible at https://localhost:8445.

## Authentication
This application uses OAuth 2.0 with JWT for authentication and authorization.  
This capability is provided using auth8 microservice from Ricardo Petronilho (https://github.com/RicardoPetronilho98/auth8/tree/develop). Please refer to documentation in order to setup the service.
 
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

## Validation
The service includes comprehensive validation for all inputs:
- Game title, platform, genre and developer validation
- Date format validation
- Query parameter validation

## Logging
- Enhanced Traceability:
  - Automatic injection of transactionId, traceId, and log point into every log event.
  - Capture execution timestamps and durations with nanosecond precision.
- Observability-Ready:
  - Structured logs for seamless integration into ELK, Grafana Loki, AWS CloudWatch, and OpenTelemetry.
  - Designed for correlation of distributed logs across microservices.
- Mask sensitive fields (e.g., email, tokens, phone numbers) dynamically.
- Hide fields completely from logs when needed.

This capability is provided using a logging library from Ricardo Petronilho (https://github.com/RicardoPetronilho98/logging). Please refer to documentation in order to setup the library.

## Integration with DuxManager
The Game Manager service integrates with DuxManager for asset tracking. Each game created in the system is also registered as an asset in DuxManager with the following attributes:
- `externalId`: Game ID
- `type`: "game"
- `permissionPolicy`: "owner"
- `artifactInformation`: Contains group, artifact, and version details

## Next Features
- CI/CD pipeline.

## Potential Tracktainment Upgrades
- **Review Microservice**: A microservice to handle reviews of books, games and movies.
- **Recommendation Microservice**: A microservice to handle books, games and movies recommendations based on what the user has consumed so far.
- **Notification Microservice** : A microservice to send notifications to users about recommendations.
