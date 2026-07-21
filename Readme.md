# 🏥 Hospital Management System (Backend)

An enterprise-level Hospital Management System backend built with **Java 21**, **Spring Boot 3**, **Spring Security**, **JWT Authentication**, **Hibernate**, and **MySQL**.

This project demonstrates enterprise backend development practices including layered architecture, RESTful APIs, role-based authentication, DTO mapping, validation, exception handling, and secure API design.

---

## 🚀 Features

### Authentication & Authorization
- JWT Authentication
- Spring Security
- Role-Based Access Control
- Password Encryption using BCrypt
- Secure Login API

### Department Management
- Create Department
- Update Department
- Delete Department
- Get Department by ID
- Get All Departments

### Doctor Management
- Create Doctor
- Update Doctor
- Delete Doctor
- Get Doctor by ID
- Get All Doctors
- Get Doctors by Department
- Get Available Doctors

### Prescription Management
- Create Prescription
- Update Prescription
- Get Prescription
- Business Rule Validation
- Auto-generated Prescription Number

### Enterprise Features
- RESTful API Design
- DTO Pattern
- Mapper Pattern
- Layered Architecture
- Global Exception Handling
- Request Validation
- Logging
- Swagger/OpenAPI Documentation
- MySQL Database Integration

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate
- JWT
- Maven
- Lombok

### Database
- MySQL

### API Documentation
- Swagger / OpenAPI

### Build Tool
- Maven

---

## 📂 Project Structure

```
src
├── main
│   ├── java
│   │   └── com.shaarky.hms
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── enums
│   │       ├── exception
│   │       ├── mapper
│   │       ├── repository
│   │       ├── security
│   │       ├── service
│   │       └── util
│   └── resources
│       ├── application.properties
│       └── static
```

---

## 🔐 Authentication

The application uses **JWT (JSON Web Token)** authentication.

Workflow:

1. Register User
2. Login
3. Receive JWT Token
4. Pass JWT in Authorization Header

```
Authorization: Bearer <your_token>
```

---

## 👥 Roles

- ADMIN
- DOCTOR
- RECEPTIONIST

Each role has secure access to specific endpoints.

---

## 📖 API Documentation

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🗄️ Database

Database:

```
MySQL
```

Configure your database credentials in

```
application.properties
```

---

## ▶️ Running the Project

### Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/Hospital-Management-System.git
```

### Navigate

```bash
cd Hospital-Management-System
```

### Build

```bash
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

---

## 📌 Current Modules

| Module | Status |
|---------|--------|
| Authentication | ✅ Completed |
| Department | ✅ Completed |
| Doctor | ✅ Completed |
| Prescription | ✅ Implemented |
| Patient | 🚧 In Progress |
| Appointment | 🚧 In Progress |
| JavaFX Desktop UI | 📅 Planned |

---

## 📚 Learning Outcomes

This project demonstrates practical experience with:

- Spring Boot
- Spring Security
- JWT Authentication
- REST API Development
- JPA/Hibernate
- MySQL
- DTO Mapping
- Exception Handling
- Layered Architecture
- Enterprise Application Design
- Clean Code Principles

---

## 📈 Future Enhancements

- JavaFX Desktop Application
- Docker Support
- Unit Testing (JUnit & Mockito)
- GitHub Actions CI/CD
- Reporting Module
- Dashboard Analytics

---

## 👨‍💻 Author

**Your Name**

- GitHub: https://github.com/YOUR_USERNAME
- LinkedIn: https://linkedin.com/in/YOUR_LINKEDIN

---

## ⭐ If you found this project useful, consider giving it a star.