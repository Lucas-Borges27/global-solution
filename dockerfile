# Use Maven to build the project
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the project and package the jar
RUN mvn clean package -DskipTests

# Use OpenJDK runtime image
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*-runner.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
