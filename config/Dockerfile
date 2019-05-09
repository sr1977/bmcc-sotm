FROM gradle:5.4.0-jdk8 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:8-jre-alpine

EXPOSE 8888
COPY --from=builder /home/gradle/src/build/libs/config-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app
CMD ["java", "-jar", "config-0.0.1-SNAPSHOT.jar"]
