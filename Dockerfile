FROM openjdk:8-jdk-alpine

ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ADD /src/main/resources/application.properties /application.properties
EXPOSE 8081 3306
#CMD java -Xms1g -Xmx1g -DLOG_HOME=/var/log/tenant/ -Dspring.jmx.enabled=false -cp app:app/lib/* com.motows.tenant.TenantApplication  --spring.profiles.active=prod --platform.credentials.file=/application.properties --server.port=8081
CMD java -cp app:app/lib/* com.motows.tenant.TenantApplication
