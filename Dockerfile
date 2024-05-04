#
# Build stage
#
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package 

#
# Package stage
#
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/estate-0.0.1-SNAPSHOT.jar estate.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","estate.jar"]