# Bank Account Kata

A REST API implementing the **Bank Account Kata** using **Java 21** and **Spring Boot 3.5**.

The project was developed incrementally following the **Red → Green → Refactor** cycle of **Test-Driven Development (TDD)** and then refactored into a **lightweight Hexagonal Architecture (Ports & Adapters)** with a rich domain model.

---

## Features

- Deposit money
- Withdraw money
- Retrieve the current balance
- View account statement
- Print a formatted account statement
- Input validation using Bean Validation
- Global exception handling
- OpenAPI / Swagger documentation
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

The project follows a lightweight **Hexagonal Architecture (Ports & Adapters)**.

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
AccountService
   │        │
   ▼        ▼
Account   StatementPrinter
              ▲
              │
   TextStatementPrinter
```

Dependency direction always points toward the application core.

The domain layer has **no dependency on Spring Framework, HTTP, OpenAPI or any infrastructure component**.

---

## Design Decisions

This implementation focuses on the business requirements of the kata while keeping the architecture simple and extensible.

- The domain model contains the business rules and remains independent from Spring Framework.
- The application follows a lightweight Hexagonal Architecture, separating the domain from the REST API and infrastructure.
- A `Clock` is injected to make date-dependent behavior deterministic and easy to test.
- The application stores data in memory, as persistence is explicitly out of scope for this kata.

---

## Project Structure

```text
src/main/java/com/sg/kata/bankaccount
├── adapter
│   ├── in
│   │   └── web
│   └── out
├── application
│   ├── port
│   └── service
├── config
└── domain
    ├── exception
    └── model
```

---

## Prerequisites

- Java 21
- Maven 3.9+

---

## Running the Application

Start the application:

```bash
mvn spring-boot:run
```

The application starts on:

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

Interactive API documentation is available through Swagger UI once the application is running.

### Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

### OpenAPI Specification

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

Response

```json
{
  "balance": 100.00
}
```

---

### Withdraw

```bash
curl -X POST http://localhost:8080/api/v1/account/withdrawals \
-H "Content-Type: application/json" \
-d '{"amount":40.00}'
```

Response

```json
{
  "balance": 60.00
}
```

---

### Current Balance

```bash
curl http://localhost:8080/api/v1/account/balance
```

Example response

```json
{
  "balance": 60.00
}
```

---

### Account Statement

```bash
curl http://localhost:8080/api/v1/account/statement
```

Example response

```json
[
  {
    "type": "DEPOSIT",
    "date": "2026-08-04",
    "amount": 100.00,
    "balance": 100.00
  },
  {
    "type": "WITHDRAWAL",
    "date": "2026-08-04",
    "amount": -40.00,
    "balance": 60.00
  }
]
```

---

### Print Statement

```bash
curl http://localhost:8080/api/v1/account/statement/print
```

Example response

```text
OPERATION | DATE | AMOUNT | BALANCE
DEPOSIT | 04/08/2026 | 100.00 | 100.00
WITHDRAWAL | 04/08/2026 | -40.00 | 60.00
```

---

## Error Handling

| HTTP Status | Description |
|-------------|-------------|
| **400 Bad Request** | Invalid request or invalid amount |
| **409 Conflict** | Insufficient funds |

Examples:

- Negative amount
- Zero amount
- Null amount
- Withdrawal greater than the current balance

---

## Testing Strategy

The project includes:

- Domain unit tests
- Application service tests
- REST controller tests using MockMvc
- Bean Validation tests
- Global exception handling tests

Run the complete test suite with:

```bash
mvn clean test
```

---

## Future Improvements

For a production-ready banking application, the following enhancements could be added:

- Persistent storage through a repository adapter
- Multiple accounts
- Transaction identifiers
- Authentication and authorization
- Pagination for account statements
- Audit logging
- Docker support
- CI/CD pipeline
- Observability (Micrometer / Prometheus)

---

## Author

Developed by **Manel Ben Salah**