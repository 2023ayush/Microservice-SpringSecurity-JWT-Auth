FROM openjdk:17
COPY target/user-service.jar /usr/app/
WORKDIR /usr/app/
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "user-service.jar"]