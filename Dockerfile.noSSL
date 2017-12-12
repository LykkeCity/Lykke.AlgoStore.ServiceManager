FROM java:8
RUN mkdir -p /usr/src/algo
#ADD docker-certs  /usr/src/algo/docker-certs
ADD keystore.jks   /usr/src/algo/
RUN chmod 600     /usr/src/algo/keystore.jks
COPY target/algostoremanager-0.0.1-SNAPSHOT.jar /usr/src/algo/algomanager.jar
COPY Dockerfile.java     /usr/src/algo/
COPY Dockerfile.jar.java     /usr/src/algo/
COPY entrypoint.sh      /usr/src/algo/entrypoint.sh
WORKDIR /usr/src/algo/

EXPOSE 8080
ENTRYPOINT ./entrypoint.sh