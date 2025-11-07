# Script IoT

Technical knowledge test project - IoT script management system with Spring Boot backend and Angular frontend.

## Description

This is a full-stack application for managing IoT scripts. It consists of a Spring Boot REST API backend and an Angular frontend, providing a complete solution for creating, managing, and executing IoT scripts.

## Features

- RESTful API for script management
- Angular web interface
- CRUD operations for scripts
- Docker Compose setup for easy deployment
- Swagger API documentation

## Architecture

### Backend (script-iot-core)
- **Framework**: Spring Boot
- **Language**: Java
- **API**: RESTful endpoints
- **Database**: Configured via application.properties

### Frontend (script-iot-ui)
- **Framework**: Angular
- **Language**: TypeScript
- **UI**: Modern web interface

## Installation

### Prerequisites
- Java 11 or higher
- Maven 3.x
- Node.js and npm
- Docker and Docker Compose (optional)

### Backend Setup

1. Navigate to the backend directory:
```bash
cd script-iot-core
```

2. Build and run:
```bash
mvn spring-boot:run
```

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd script-iot-ui
```

2. Install dependencies:
```bash
npm install
```

3. Run development server:
```bash
ng serve
```

## Usage

### URLs

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **REST API**: http://localhost:8080/api/v1/scripts
- **Frontend**: http://localhost:4200/

### API Endpoints

- `GET /api/v1/scripts` - List all scripts
- `GET /api/v1/scripts/{id}` - Get script by ID
- `POST /api/v1/scripts` - Create new script
- `PUT /api/v1/scripts/{id}` - Update script
- `DELETE /api/v1/scripts/{id}` - Delete script

## Docker Deployment

Use Docker Compose for easy deployment:

```bash
cd script-iot-core
docker-compose up
```

## Project Structure

```
script-iot/
├── script-iot-core/        # Spring Boot backend
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/fernandoog/
│   │               ├── controller/
│   │               ├── model/
│   │               ├── repository/
│   │               └── ScriptIotCoreApplication.java
│   └── docker-compose.yml
├── script-iot-ui/          # Angular frontend
│   └── src/
│       └── app/
│           ├── create-script/
│           ├── launch-script/
│           ├── script-details/
│           ├── script-list/
│           └── update-script/
└── doc/                    # Documentation and examples
```

## Author

Fernando Ortega Gorrita (@fernandoog)

## License

See LICENSE file for details.
