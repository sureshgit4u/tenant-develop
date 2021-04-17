FROM openjdk:8-jdk-alpine

#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ADD /src/main/resources/application.properties /application.properties
#EXPOSE 8081 3306
#CMD java -cp app:app/lib/* com.motows.tenant.TenantApplication

ADD target/motows-app.jar motows-app.jar
EXPOSE 8081 3306 80
ENTRYPOINT ["java","-jar","motows-app.jar"]

