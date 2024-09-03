FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY /target/CommentService-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT ["java", "-jar", "CommentService-0.0.1-SNAPSHOT.jar"]
