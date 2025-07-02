FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the built JAR from the Maven project
COPY qr-code-generator/target/qr_code_generator-0.0.1-SNAPSHOT.jar app.jar

# Expose the Spring Boot port
EXPOSE 1111

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

