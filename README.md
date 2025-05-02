# 🧑‍💼 Employee Management System (EMS)

A full-stack Java-based Employee Management System (EMS) for managing employee data, departments, and addresses within an organization. Designed for HR and administrative use cases in a software company.

---

## 📌 Tech Stack

- **Backend**: Java 17, Spring Boot 3.x
- **Database**: PostgreSQL
- **ORM**: Hibernate / JPA
- **Build Tool**: Maven
- **API Documentation**: Swagger (optional)
- **Lombok**: For boilerplate reduction
- **UUID & Sequence Support**: Mixed ID strategy

---

## ✅ Features

- CRUD operations on **Employees**, **Departments**, and **Addresses**
- Department-wise employee filtering with salary threshold
- Proper JPA relationships:
    - `@ManyToOne` between Employee and Department
    - `@OneToOne` between Employee and Address
    - Bidirectional `@OneToMany` for Department to Employee
- Sequence-based ID generation for PostgreSQL
- Lazy loading to avoid N+1 query issues
- DTO layer for API responses
- Enum-based department management

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- PostgreSQL 13+
- Maven 3.6+
- (Optional) Docker

### Database Setup

1. Create a schema (e.g., `drs_ems`)
2. Create required sequences manually (or let JPA generate them):

```sql
CREATE SEQUENCE department_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE employee_id_seq START 1 INCREMENT 1;
```
3. Run the SQL scripts under src/main/resources/script/init.sql to populate dummy address, department, and employee records (10,000+ supported)



### ⚙️ Configuration
Edit application.yml or application.properties:

yaml
Copy
Edit
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/drs_ems
    username: postgres
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
        show_sql: true

### 🔄 API Endpoints
👤 Employees

| Method   | Endpoint                              | Description                               |
| -------- | ------------------------------------- | ----------------------------------------- |
| `GET`    | `/employees/{id}`                     | Fetch employee by ID                      |
| `GET`    | `/employees?department=X&minSalary=Y` | Filter employees by department and salary |
| `POST`   | `/employees`                          | Create a new employee                     |
| `PUT`    | `/employees/{id}`                     | Update employee                           |
| `DELETE` | `/employees/{id}`                     | Delete employee                           |


### 📁 Project Structure

```plaintext
com.drs.ems
├── controller       # REST controllers
├── service          # Business logic layer
├── repository       # Spring Data JPA repositories
├── entity           # JPA entities
├── dto              # Data Transfer Objects
├── enums            # Enum definitions (e.g., DepartmentNames)
└── util             # Utility classes (e.g., DTO builders)
```
### 🔐 Future Enhancements
Spring Security for authentication & authorization

Pagination and sorting

Swagger/OpenAPI documentation

React frontend

Dockerized deployment

GitLab pipeline

gRPC integration with other service

kafka integration

### 👨‍💻 Author
Built by Deepak Ranjan Sethy
Feel free to contribute or raise issues.

