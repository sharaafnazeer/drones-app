FROM openjdk:17
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} drone-docker.jar
ENTRYPOINT ["java","-jar","/drone-docker.jar"]