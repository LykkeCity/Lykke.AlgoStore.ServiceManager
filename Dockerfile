FROM java:8
RUN mkdir -p /usr/src/serviceAlgo
ADD docker-certs   /usr/src/serviceAlgo/docker-certs
ADD keystore.jks   /usr/src/serviceAlgo/
RUN chmod 600     /usr/src/serviceAlgo/keystore.jks
COPY target/algostoremanager-0.0.1-SNAPSHOT.jar /usr/src/serviceAlgo/algomanager.jar
COPY Dockerfile.java     /usr/src/serviceAlgo/
COPY Dockerfile.jar.java     /usr/src/serviceAlgo/
WORKDIR /usr/src/serviceAlgo/
EXPOSE 8443
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","algomanager.jar"]