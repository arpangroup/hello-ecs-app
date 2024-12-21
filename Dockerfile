# Use a lightweight base image with Java
# FROM openjdk:22-jdk-slim
# FROM adoptopenjdk:21-jre-hotspot # lighter
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/ecs-app-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]