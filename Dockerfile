# ---------- Build stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn -B -q -e -C dependency:go-offline

COPY src ./src
RUN mvn clean verify

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=builder /app/target/authentication-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java","-jar","/app/app.jar"]