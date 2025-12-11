FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

#Copy the already-built JAR from target/
COPY target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]