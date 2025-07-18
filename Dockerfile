FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:17
WORKDIR /app

COPY --from=build /app/target/*-runner.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
