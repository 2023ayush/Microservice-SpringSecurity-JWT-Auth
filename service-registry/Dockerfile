FROM openjdk:17
COPY target/service-registry.jar /usr/app/
WORKDIR /usr/app/
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "service-registry.jar"]