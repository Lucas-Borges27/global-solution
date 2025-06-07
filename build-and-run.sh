#!/bin/bash
# Build the project with Maven
mvn clean package -DskipTests

# Build the Docker image using Dockerfile.jvm
docker build -t myapp -f src/main/docker/Dockerfile.jvm .

# Run the Docker container
docker run -p 8080:8080 myapp
