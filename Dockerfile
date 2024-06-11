#
# Build stage
#
FROM maven:3.8.2-openjdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk
COPY --from=build /target/estate-0.0.1-SNAPSHOT.jar estate.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "estate.jar"]
