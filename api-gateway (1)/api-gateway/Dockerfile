FROM openjdk:17
COPY target/api-gate.jar /usr/app/
WORKDIR /usr/app/
EXPOSE 5555
ENTRYPOINT ["java", "-jar", "api-gate.jar"]