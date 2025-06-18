# Tracker-Backend
A smart and simple React + Spring Boot full-stack application to track income, expenses, and budgeting — all in one place.

---

## 🚀 Features

- User Registration and Login (with JWT Authentication)
- Add, update, delete, and list transactions
- Supports "credit" and "debit" types
- Integrated with MySQL using Spring Data JPA
- Follows RESTful API design principles
- CORS enabled for frontend integration

---

## 🛠 Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Web**, **Spring Security**, **Spring Data JPA**
- **MySQL**
- **Maven**
- **JWT (JSON Web Token)** for Authentication

---
# ⚙️ Getting Started

### 🧑‍💻 Prerequisites

- Java 17+
- Maven
- MySQL (or any SQL DB)
- Git

### 🔧 Setup Instructions

1. **Clone the repository**
- git clone https://github.com/yourusername/Tracker-Backend.git
- cd Tracker-Backend
2. **Update DB configuration**
- Goto properties file
- Edit src/main/resources/application.properties:
    application.properties
        spring.datasource.url=jdbc:mysql://{YourDatabaseURL}:{yourDatabasePortNumber}/trackerdb
        spring.datasource.username=yourusername
        spring.datasource.password=yourpassword
        spring.jpa.hibernate.ddl-auto=update
3. **Build and run the app**
- ./mvnw clean install
- ./mvnw spring-boot:run
4. **Server will start at: **
- http://localhost:8080

# API Endpoints
| Method | Endpoint                 | Description             |
| ------ | ------------------------ | ----------------------- |
| POST   | `/api/auth/register`     | Register a new user     |
| POST   | `/api/auth/login`        | Login and get JWT token |
| POST   | `/api/transactions/{id}` | Add a transaction       |
| PUT    | `/api/transactions/{id}` | Update transaction      |
| DELETE | `/api/transactions/{id}` | Delete transaction      |

Note: 
- id is userId
- These are the current API's being used at frontend

# Testing
###Use tools like:
- Postman or Insomnia for API testing
- mvn test for unit/integration tests (if configured)