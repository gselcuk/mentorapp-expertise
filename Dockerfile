FROM openjdk:13-jdk-alpine
VOLUME /tmp
COPY target/*.jar mentorapp-expertise.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/mentorapp-expertise.jar"]