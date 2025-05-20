# Restaurant Reservation System 🍽️

Web system for managing restaurant table reservations, developed with Spring Boot and JavaScript.

## Overview

This project allows customers to book tables at a restaurant, view availability, and enables administrators to manage tables and reservations. It features JWT authentication, an admin interface, and a documented RESTful API.

## Features

- User authentication (login/register)
- View available tables
- Make and cancel reservations
- Admin interface for table management
- Documented RESTful API

## Technologies Used

- **Backend:**
  - Java 17
  - Spring Boot 3.2.3
  - Spring Security
  - JWT for authentication
  - MySQL (or H2 for testing)
- **Frontend:**
  - HTML5, CSS3, JavaScript, Bootstrap 5

## Prerequisites

- Java 17 or higher
- Maven
- MySQL running locally (or H2 for testing)
- Modern web browser

## Installation & Running

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Arkleee/Restaurant-Reservations.git
   cd Restaurant-Reservations
   ```

2. **Configure the database:**

   - For MySQL, edit `src/main/resources/application.properties` with your credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/reservasdb?createDatabaseIfNotExist=true
     spring.datasource.username=YOUR_USER
     spring.datasource.password=YOUR_PASSWORD
     ```

   - For testing with H2, use the following configuration:
     ```properties
     spring.datasource.url=jdbc:h2:mem:testdb
     spring.datasource.driverClassName=org.h2.Driver
     spring.datasource.username=sa
     spring.datasource.password=
     spring.h2.console.enabled=true
     ```

3. **Run the project:**

   - On Unix/macOS:
     ```bash
     ./mvnw spring-boot:run
     ```
   - On Windows:
     ```bash
     mvnw spring-boot:run
     ```

4. **Access the application:**
   - [http://localhost:8080](http://localhost:8080)

## Test Credentials

- **Regular user:**
  - Email: `test@example.com`
  - Password: `password`

- **Administrator:**
  - Email: `admin@example.com`
  - Password: `adminpassword`

## API Usage Examples

### List available tables
```http
GET /api/mesas/disponiveis
```

### Create a new table (admin)
```http
POST /api/mesas
Content-Type: application/json
Authorization: Bearer <token>

{
  "numero": 10,
  "lugares": 4,
  "status": "DISPONIVEL"
}
```

### Make a reservation
```http
POST /api/reservas
Content-Type: application/json
Authorization: Bearer <token>

{
  "mesaId": 1,
  "dataHora": "2025-05-20T19:00:00"
}
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── adraga_mesas/
│   │           └── reservasrestaurante/
│   │               ├── config/
│   │               ├── controller/
│   │               ├── dto/
│   │               ├── model/
│   │               ├── repository/
│   │               └── service/
│   └── resources/
│       ├── static/
│       │   ├── css/
│       │   ├── js/
│       │   └── images/
│       └── application.properties
```

## Branches

- **main**: stable branch ready for production.
- **develop**: development branch for new features and fixes.

To create and use the development branch:
```bash
git checkout -b develop
# Make changes, commits, and push as usual
```

## How to Contribute

1. Fork the project
2. Create a branch for your feature/fix:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m 'Description of the feature'
   ```
4. Push to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```
5. Open a Pull Request to the `develop` branch of this repository.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
