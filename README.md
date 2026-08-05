# Bank Account Kata

A REST API implementing the **Bank Account Kata** using **Java 21** and **Spring Boot 3.5**.

The project was developed using **Test-Driven Development (TDD)** and follows a lightweight **Hexagonal Architecture (Ports & Adapters)**.

---

## Features

- Deposit money
- Withdraw money
- Retrieve the current balance
- View account statement
- Print a formatted account statement
- Request validation
- Global exception handling
- Swagger / OpenAPI documentation
- In-memory storage (no persistence)

---

## Technology Stack

- Java 21
- Spring Boot 3.5
- Maven
- Spring MVC
- Bean Validation
- Springdoc OpenAPI
- JUnit 5
- Mockito
- MockMvc

---

## Architecture

```text
REST API
    │
    ▼
AccountController
    │
    ▼
AccountUseCase
    │
    ▼
BankAccountService
   │        │
   ▼        ▼
Account   StatementPrinter
              ▲
              │
   TextStatementPrinter
```

The domain layer is independent of Spring Framework and infrastructure components.

---

## Running the Application

Start the application:

```bash
mvn spring-boot:run
```

The application will be available at:

```
http://localhost:8080
```

---

## Running the Tests

Run all tests:

```bash
mvn clean test
```

---

## API Documentation

- **Swagger UI**

```
http://localhost:8080/swagger-ui/index.html
```

- **OpenAPI Specification**

```
http://localhost:8080/v3/api-docs
```

---

## REST Endpoints

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/v1/account/deposits` | Deposit money |
| POST | `/api/v1/account/withdrawals` | Withdraw money |
| GET | `/api/v1/account/balance` | Retrieve current balance |
| GET | `/api/v1/account/statement` | Retrieve account statement |
| GET | `/api/v1/account/statement/print` | Print formatted statement |

---

## API Examples

### Deposit

```bash
curl -X POST http://localhost:8080/api/v1/account/deposits \
-H "Content-Type: application/json" \
-d '{"amount":100.00}'
```

### Withdraw

```bash
curl -X POST http://localhost:8080/api/v1/account/withdrawals \
-H "Content-Type: application/json" \
-d '{"amount":40.00}'
```

---

## HTTP Status Codes

| Status | Description |
|---------|-------------|
| **200** | Request completed successfully |
| **400** | Invalid request or validation error |
| **409** | Insufficient funds |

---

## Author

Developed by **Manel Ben Salah**