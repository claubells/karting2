FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} rates-service.jar
ENTRYPOINT ["java","-jar","/rates-service.jar"]