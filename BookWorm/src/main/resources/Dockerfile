FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} bookworm.jar
ENTRYPOINT ["java","-jar","/bookworm.jar"]