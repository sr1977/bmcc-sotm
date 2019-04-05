FROM openjdk:8-jre-alpine
WORKDIR /app
EXPOSE 8888
ADD ./build/libs/config-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "config-0.0.1-SNAPSHOT.jar"]
