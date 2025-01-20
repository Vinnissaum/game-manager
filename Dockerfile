FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /home/app
COPY pom.xml /home/app
COPY src /home/app/src
RUN mvn install
#
# Package stage
#
FROM eclipse-temurin:21-jdk-jammy
ARG JAR_FILE=/home/app/target/*.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]