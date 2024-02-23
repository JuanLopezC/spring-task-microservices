# FROM eclipse-temurin:17-jdk-alpine
FROM openjdk:17
VOLUME "/tmp"
COPY target/*.jar app.jar
EXPOSE 30201
ENTRYPOINT [ "java", "-jar", "/app.jar" ]