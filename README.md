# 🛡️ Microservice Spring Security Project

This project demonstrates a **Spring Boot Microservices Architecture** with **Spring Security**, **JWT Authentication**, **Eureka Service Registry**, and an **API Gateway**. All services are containerized using Docker and orchestrated with Docker Compose.

## 🌐 Live Demo

- **Frontend (Netlify):** [View Live](https://sprightly-toffee-322be7.netlify.app/login)
- **API Documentation (Postman):** [View on Postman](https://documenter.getpostman.com/view/33677881/2sB34cnhJa)
- **Docker Hub:** [ayush4857](https://hub.docker.com/repositories/ayush4857)

> 🕐 Backend hosted on AWS EC2 — may take a few seconds to start when idle.

Best Practices Implemented

✅ Global Exception Handling – centralized error handling for all services using @RestControllerAdvice.

✅ DTO Validation – input validated with @Valid and custom constraints for safer requests.

✅ Logging – SLF4J/Logback logging implemented in controllers and services for easier debugging and monitoring.

✅ Containerization – all microservices containerized with Docker and orchestrated via Docker Compose.

✅ Security – JWT authentication with role-based access control.

✅ Microservice Architecture – separated services with Eureka service discovery and API Gateway routing.


## 🏗️ Project Structure

| Module           | Description                                  |
|------------------|----------------------------------------------|
| `api-gateway/`   | Spring Cloud Gateway with JWT authentication |
| `auth-service/`  | Auth microservice – Login / Register endpoints |
| `user-service/`  | Protected User APIs (JWT secured)             |
| `service-registry/` | Eureka Server for service discovery       |

---

## 🛠️ Tech Stack

- Java 17+  
- Spring Boot  
- Spring Cloud  
- Spring Security + JWT  
- Eureka Discovery Server  
- Spring Cloud Gateway  
- Maven  
- Postman (or Swagger) for API testing  
- Docker + Docker Compose  

---

## ⚙️ Services Overview

| Service           | Port | Description                      |
|-------------------|------|----------------------------------|
| Eureka Registry   | 8761 | Service Discovery Server         |
| Auth Service      | 1122 | Handles login & registration     |
| User Service      | 8081 | JWT-secured user endpoints       |
| API Gateway       | 5555 | Routes & secures all API traffic |

---

## 🔐 Authentication Flow

1. User logs in via `/auth/api/v1/auth/login`  
2. A JWT token is returned upon successful login  
3. Client sends the token in the `Authorization: Bearer <token>` header  
4. API Gateway validates the token and routes the request to internal services  
5. Secured services process only if the token is valid  

---

## 🐳 Docker Deployment

All services are containerized and deployed using Docker Compose.

---
## 🧩 Summary

This project demonstrates a **production-ready microservice system** using:
- Spring Boot + JWT for secure authentication  
- Spring Cloud for service discovery and routing  
- Docker for microservice orchestration  
- Netlify + AWS EC2 for full-stack deployment  

💻 **Designed, implemented, and deployed by [Ayush Guragain](https://github.com/2023ayush)**
