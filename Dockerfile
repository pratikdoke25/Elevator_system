# First stage: build the application
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

# Second stage: create the Docker image
FROM openjdk:17-jdk-alpine
RUN mkdir /app
WORKDIR /app
# Copy the built JAR file from the first stage
COPY --from=build /app/target/Elevators-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "Elevators-0.0.1-SNAPSHOT.jar"]