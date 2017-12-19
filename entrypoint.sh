#!/bin/bash

env


if [ -z "$JAVA_OPTIONS" ]; then
    JAVA_OPTIONS=-Xms256M -Xmx512M
fi


java -DDOCKER_CERT_PATH=$DOCKER_CERT_PATH \
-Dspring.config.location=$ALGOSTORE_CONFIG \
-Djava.security.egd=file:/dev/./urandom \
-D -XX:+PrintFlagsFinal -XX:+PrintGCDetails $JAVA_OPTIONS \
-jar /usr/src/algo/algomanager.jar