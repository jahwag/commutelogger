# build stage
FROM adoptopenjdk/openjdk8:alpine-slim
COPY target/*.jar app.jar
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]