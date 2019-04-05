FROM openjdk:8-jre-alpine
WORKDIR /app
EXPOSE 9090
ADD ./build/libs/strava-0.0.1-SNAPSHOT.jar .
ADD ./wait-for.sh .
CMD chmod +x *.sh
