#!/bin/bash

kubectl create secret docker-registry gcr-json-key \
--docker-server=gcr.io \
--docker-username=_json_key \
--docker-password="$(cat ~/Downloads/BMCC\ SOTM-7f02f9fbb2fb.json)" \
--docker-email=srisdon@gmail.com
