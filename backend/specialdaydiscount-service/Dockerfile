FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} specialdaydiscount-service.jar
ENTRYPOINT ["java", "-jar","/specialdaydiscount-service.jar"]