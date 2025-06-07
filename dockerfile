# ---------- BUILD ----------
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -Dquarkus.package.type=uber-jar -B

# ---------- RUNTIME ----------
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*-runner.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
