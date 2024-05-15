FROM maven:3.9.6-eclipse-temurin-22-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:22-slim
COPY --from=build /target/msvc-atencionmedica-0.0.1-SNAPSHOT.jar msvc-atencionmedica.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "msvc-atencionmedica.jar"]
