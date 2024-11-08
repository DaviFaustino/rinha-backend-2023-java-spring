FROM maven:3.8.5-openjdk-17 as builder
WORKDIR /app
COPY pom.xml .
COPY src /app/src
RUN mvn test
RUN mvn package -Pproduction -DskipTests

FROM eclipse-temurin:17.0.12_7-jre
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY --from=builder /app/target/rinhabackend-1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080
