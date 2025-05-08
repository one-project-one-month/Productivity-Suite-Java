FROM gradle:8.5-jdk17 AS build
WORKDIR /app

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

RUN chmod +x gradlew

RUN ./gradlew build --no-daemon || return 0

COPY . /app

RUN ./gradlew clean build -x test --no-daemon

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/build/libs/*.war app.war
COPY .env .env

ENTRYPOINT ["java", "-jar", "app.war"]
