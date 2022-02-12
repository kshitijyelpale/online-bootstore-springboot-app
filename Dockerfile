FROM openjdk:11-oracle
EXPOSE 8080
ARG JAR_FILE=book.jar
ADD ${JAR_FILE} book.jar
ENTRYPOINT ["java", "-jar", "/book.jar"]