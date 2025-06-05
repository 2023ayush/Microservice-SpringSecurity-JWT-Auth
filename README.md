# Microservice Spring Security Project

This project demonstrates a **Spring Boot Microservices Architecture** with **Spring Security**, **JWT Authentication**, **Eureka Service Registry**, and **API Gateway**.

## 🏗️ Project Structure
api-gateway/              # Spring Cloud Gateway with JWT security
auth-service/             # Authentication service (Login/Register)
user-service/             # User microservice (secured)
service-registry/         # Eureka Server for service discovery

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Cloud**
- **Spring Security** with **JWT Authentication**
- **Eureka Discovery Server**
- **Spring Cloud Gateway**
- **Maven** for project build and dependency management
- **Postman** / **Swagger** for API testing and documentation


## ⚙️ Services Overview

| Service         | Port | Description                        |
|-----------------|------|------------------------------------|
| Eureka Registry | 8761 | Service Discovery Server           |
| Auth Service    | 8081 | Login / Register APIs              |
| User Service    | 8082 | User APIs (secured with JWT)       |
| API Gateway     | 8080 | Routes requests + Security Layer   |



🔐 Authentication Flow

User logs in via /auth/login.

JWT token is returned.

Token is sent in Authorization: Bearer <token> header for all secured requests.

Gateway filters and verifies token before routing
## 📬 API Documentation

- 👉 [Admin Login](https://documenter.getpostman.com/view/33677881/2sB2x2JZBM)
- 👉 [Register Admin](https://documenter.getpostman.com/view/33677881/2sB2x2JZBP)

