FROM openjdk:16
ADD target/netflix-docker-application.jar netflix-docker-application.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "netflix-docker-application.jar"]