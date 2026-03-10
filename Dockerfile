FROM maven:3.9.12-eclipse-temurin-21-alpine

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean pakage -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar wallet.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "wallet.jar"]
