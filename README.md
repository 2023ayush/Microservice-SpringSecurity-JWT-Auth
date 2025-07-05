# 🛡️ Microservice Spring Security Project

This project demonstrates a **Spring Boot Microservices Architecture** with **Spring Security**, **JWT Authentication**, **Eureka Service Registry**, and an **API Gateway**.  
All services are containerized using Docker and orchestrated with Docker Compose.

---

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
| Auth Service      | 8081 | Handles login & registration     |
| User Service      | 8082 | JWT-secured user endpoints       |
| API Gateway       | 8080 | Routes & secures all API traffic |

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

### 📦 Docker Hub

✅ Docker images are available on Docker Hub:  
👉 [View Docker Hub Repositories](https://hub.docker.com/repositories/ayush4857)

📬 API Documentation
👉 [View Full Postman API Docs](https://documenter.getpostman.com/view/33677881/2sB34cnhJa)

✅ Usage Summary
Register/Login using auth-service via API Gateway

Get a JWT token

Call protected routes in user-service using the token

All communication flows through api-gateway

Eureka handles service discovery
