FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY /target/UserService-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT ["java", "-jar", "UserService-0.0.1-SNAPSHOT.jar"]

