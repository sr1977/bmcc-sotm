#!/bin/bash

#./gradlew clean build assemble
docker build -t config .
docker tag config gcr.io/bmcc-sotm/config
docker push gcr.io/bmcc-sotm/config
