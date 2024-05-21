FROM openjdk:22

WORKDIR /app

COPY /target/msvc-atencionpaciente-0.0.1-SNAPSHOT.jar .

EXPOSE 8005

ENTRYPOINT ["java", "-jar", "msvc-atencionpaciente-0.0.1-SNAPSHOT.jar"]
