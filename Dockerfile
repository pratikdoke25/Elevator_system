FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/Elevators-0.0.1-SNAPSHOT.jar elevators-1.0.0.jar
ENTRYPOINT ["java","-jar","/elevators-1.0.0.jar"]
