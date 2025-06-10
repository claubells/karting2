FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} peoplediscount-service.jar
ENTRYPOINT ["java","-jar","/peoplediscount-service.jar"]