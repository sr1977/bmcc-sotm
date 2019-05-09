#!/bin/bash

#./gradlew clean build assemble
docker build -t strava .
docker tag strava gcr.io/bmcc-sotm/strava
docker push gcr.io/bmcc-sotm/strava
