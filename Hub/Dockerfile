FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY /target/Hub-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT ["java", "-jar", "Hub-0.0.1-SNAPSHOT.jar"]