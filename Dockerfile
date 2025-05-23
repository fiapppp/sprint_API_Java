# -------- Stage 1: Build the application with Maven --------
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Define working directory inside the container
WORKDIR /build

# Copy all project files (pom.xml, src/) into the container
COPY . .

# Build the Quarkus application, skipping tests
RUN mvn clean package -DskipTests

# -------- Stage 2: Create the runtime image --------
FROM eclipse-temurin:17-jdk-alpine

# Define working directory for the runtime
WORKDIR /app

# Copy the runnable JAR from the build stage
COPY --from=build /build/target/*-runner.jar app.jar

# Expose the port that Quarkus uses by default
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]