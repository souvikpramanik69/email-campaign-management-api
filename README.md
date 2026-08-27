# Email Campaign Management API

A RESTful API for creating and managing email campaigns, recipients, scheduling, and campaign statistics.

The application is built with Spring Boot and PostgreSQL and supports both local development and Docker-based deployment.

## Features

- Create and manage email campaigns
- Retrieve campaigns by ID
- List campaigns with pagination and sorting
- Add recipients to campaigns
- Prevent duplicate recipients
- Schedule campaigns
- Campaign status management
- Campaign statistics
- Request validation
- Global exception handling
- PostgreSQL database integration
- Swagger/OpenAPI documentation
- Unit and controller tests
- Docker and Docker Compose support

## Tech Stack
- Java 24
- Spring Boot 4.1.1
- Spring MVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- JUnit
- Mockito
- MockMvc
- Swagger / OpenAPI
- Docker
- Docker Compose



# Startup Guide

This guide explains how to start the Email Campaign Management API locally and using Docker.

---

## Prerequisites

Make sure the following are installed:

- Java 24
- Maven
- PostgreSQL
- Docker
- Docker Compose

Verify the installations:


java -version
mvn -version
psql --version
docker --version
docker compose version



# Project startup guide using maven cli

## (Note)
please provide postgres username and password in application-dev.yml 
because by default spring profile set in dev

### 1. Install Maven Dependencies

From the project root directory, run:

mvn clean install

====================

### 2. Run Tests

Run the complete test suite:

mvn test

Make sure all tests pass before starting the application.

========================

### 3. Start the Application

Start the Spring Boot application using Maven:

mvn spring-boot:run

================================================


# Project startup guide using docker

###  Clone the Repository

Clone the project:

git clone <repository-url>

Navigate into the project:

cd email-campaign-management-api

=================================

### 1. Install Maven Dependencies

From the project root directory, run:

mvn clean install

### 2. Configure Environment

The Docker setup runs both the Spring Boot application and PostgreSQL.

Before starting the containers, check:

docker-compose.yml

================================

### 3. Build and Start the Application

From the project root:

docker compose up --build