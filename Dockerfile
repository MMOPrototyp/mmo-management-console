# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:14-jre-alpine

ARG JAR_FILE=target/*.jar

# copy application WAR (with libraries inside)
#COPY target/spring-boot-*.war /app.war

# specify default command
#CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=test", "/app.war"]

# see also: https://github.com/spring-guides/gs-spring-boot-docker

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=default","/app.jar"]