FROM openjdk:8-jdk-alpine
WORKDIR motows
ADD target/*.jar motows-app.jar
EXPOSE 8081 3306 80
ENTRYPOINT ["java","-jar","motows-app.jar"]

