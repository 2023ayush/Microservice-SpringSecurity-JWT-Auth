FROM openjdk:17
COPY target/auth-service.jar /usr/app/
WORKDIR /usr/app/
EXPOSE 1122
ENTRYPOINT ["java", "-jar", "auth-service.jar"]