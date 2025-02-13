
FROM openjdk:latest


WORKDIR /app

COPY target/UserRegistrationSystem-0.0.1-SNAPSHOT.jar app.jar


ENTRYPOINT ["java", "-jar", "/app/app.jar"]
