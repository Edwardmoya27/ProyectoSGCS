FROM openjdk:22

WORKDIR /app

COPY /target/msvc-medico-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "msvc-medico-0.0.1-SNAPSHOT.jar"]
