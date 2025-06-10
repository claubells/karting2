FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} loyaltydiscount-service.jar
ENTRYPOINT ["java","-jar","/loyaltydiscount-service.jar"]