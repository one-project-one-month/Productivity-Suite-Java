
# 📦 Productivity Suite - Spring Boot App with Docker Compose

This is a modular, containerized Spring Boot application built with Gradle and OpenJDK 17. It follows clean architecture principles, structured into configuration, domain data, and feature modules.

---

## 🐳 Getting Started with Docker Compose

### 🔧 Prerequisites

Make sure you have the following installed:

- [Docker](https://www.docker.com/products/docker-desktop)
- [Docker Compose](https://docs.docker.com/compose/)

---

## 🚀 Running the App

### 1. Clone the Repository

```bash
git clone https://github.com/one-project-one-month/Productivity-Suite-Java.git
cd Productivity-Suite-Java
```

### 2. Build and Run with Docker Compose

```bash
docker-compose up --build
```

This command will:

1. Build the application with Gradle in a container.
2. Package it into a `.jar` file.
3. Launch it using a lightweight OpenJDK 17 image.

---

## ⚙️ Dockerfile Highlights

- **Multi-stage build**: Gradle 8.5 JDK17 for building → OpenJDK 17 Slim for running.
- **.env Support**: Configure Spring Boot environment variables without modifying the code.
- **Exposes port `8080`** for application access.

---

## 📁 Project Structure

```
src/main/java/com/_p1m/productivity_suite
├── config            # Global app configuration (e.g., annotations, request/response, exceptions)
├── data              # Domain layer (models, enums)
├── features          # Business modules (categories, currencies, users)
├── security          # Spring Security configuration and filters
├── ProductivitySuiteApplication.java
├── ServletInitializer.java

src/main/resources
└── application.yml   # Main Spring Boot config file
```

---

## 📄 Tech Stack

- **Spring Boot 3.2.5**
- **Java 17**
- **Gradle Toolchain**
- **PostgreSQL** (runtime dependency)
- **Lombok** for boilerplate reduction
- **JWT (jjwt)** for token handling
- **ModelMapper** for object mapping
- **Springdoc OpenAPI** for API docs
- **java-dotenv** to load environment variables

---

## 🛑 Stopping the App

```bash
docker-compose down
```

---

## 🧹 Clean Build Cache (Optional)

```bash
docker system prune -af
```

---

## 👨‍💻 Application Entry Point

The main class is:

```java
com._p1m.productivity_suite.ProductivitySuiteApplication
```

You can also run it without Docker using:

```bash
./gradlew bootRun
```

---

## 📬 Contribution

Feel free to fork, improve, and open pull requests.

---

MIT © 2025 One Project One Month
