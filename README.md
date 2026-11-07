# Employee Leave Management System

A web app where employees can view leave balances, apply for leave and check their leave history.

**Tech stack:** Java 17, Spring Boot, JDBC, MySQL, Thymeleaf, HTML/CSS, Maven

## Features

- Leave balances (Casual, Sick, Earned) with progress bars
- Apply for leave with validation
- Leave history with Pending, Approved and Rejected status
- REST APIs for employees and leave requests (including approve and reject)

## Architecture

Controller → Service → DAO → MySQL

## Setup

1. Install JDK 17 and MySQL.
2. Run `database/schema.sql`, then optionally `database/seed-data.sql`.
3. Set your MySQL username and password in `src/main/resources/application.properties`.
4. Start the app:
   ```bash
   mvn spring-boot:run
   ```
5. Open <http://localhost:8080> (use `/leave?employeeId=<id>` for a specific employee).

## API

| Method | Endpoint | Description |
|---|---|---|
| POST / GET | `/employees` | Add / list employees |
| GET / DELETE | `/employees/{id}` | Get / delete an employee |
| POST | `/leaves` | Apply for leave |
| GET | `/leaves/employee/{id}` | List an employee's leaves |
| PUT | `/leaves/{id}/approve` | Approve a request |
| PUT | `/leaves/{id}/reject` | Reject a request |

## Limitations and next steps

- No login or roles yet
- No approval screen in the UI
- Planned: Spring Security and a manager page for approvals

## Author

Manoshree G
