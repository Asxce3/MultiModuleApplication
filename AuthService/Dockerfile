FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY /target/AuthService-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT ["java", "-jar", "AuthService-0.0.1-SNAPSHOT.jar"]