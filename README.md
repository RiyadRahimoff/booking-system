# BookFlow

BookFlow is a backend **booking management system** built with Java and Spring Boot.
The project allows service owners to manage their services and locations, while customers can browse available services and make bookings.

## 🚀 Technologies

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* Liquibase
* Redis
* JWT
* Docker
* REST API
* Lombok

## 👥 User Roles

The system is designed around two main user types:

### Customer

* Register and login
* Browse available services
* View service and location information
* Create bookings
* Manage their bookings

### Owner

* Register as a service owner
* Create and manage services
* Add and manage service locations
* Manage bookings related to their services

## 🔐 Authentication & Authorization

* JWT-based authentication
* Role-based authorization
* Secure password handling
* Global exception handling
* User status management

## 📦 Project Structure

```text
src/main/java/com/bookflow
│
├── exception
│   └── GlobalExceptionHandler
│
├── user
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── enums
│   ├── repository
│   └── service
│       ├── abstraction
│       └── concrete
│
├── booking
├── service
├── location
└── security
```

## 🛠️ Main Features

* User registration and authentication
* Customer and Owner role separation
* Service management
* Location management
* Booking management
* Input validation
* Global exception handling
* PostgreSQL database integration
* Liquibase database migrations
* Redis integration
* RESTful API architecture

## 🗄️ Database

PostgreSQL is used as the primary relational database.

Liquibase is used to manage database schema changes and migrations.

## 🐳 Docker

The project is designed to run with Docker for easier development and deployment.

## ▶️ Running the Project

Clone the repository:

```bash
git clone https://github.com/YOUR_USERNAME/bookflow.git
```

Navigate to the project:

```bash
cd bookflow
```

Run the application with your preferred IDE or using Gradle:

```bash
./gradlew bootRun
```

## 📌 Project Status

🚧 **In Development**

New features and improvements are being added gradually.

## 🎯 Purpose

BookFlow was created as a practical Spring Boot backend project to demonstrate:

* Clean backend architecture
* REST API development
* Authentication and authorization
* Database management
* Exception handling
* Service-layer design
* Real-world booking business logic
