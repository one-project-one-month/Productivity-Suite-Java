FROM gradle:8.5-jdk17 AS build
WORKDIR /app

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

RUN chmod +x /app/gradlew

COPY . /app

RUN chmod +x /app/gradlew

RUN sed -i '/buildScan {/,/}/s/^/\/\/ /' /app/build.gradle

RUN /app/gradlew clean build

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar
COPY .env .env

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]