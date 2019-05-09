FROM gradle:5.4.0-jdk8 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:8-jre-alpine
EXPOSE 8000
COPY --from=builder /home/gradle/src/build/libs/strava-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app
CMD ["java", "-jar", "strava-0.0.1-SNAPSHOT.jar"]
