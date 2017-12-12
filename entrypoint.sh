#!/bin/bash

env

java -DDOCKER_CERT_PATH=$DOCKER_CERT_PATH \
-Dspring.config.location=$ALGOSTORE_CONFIG \
-Djava.security.egd=file:/dev/./urandom \
-jar /usr/src/algo/algomanager.jar