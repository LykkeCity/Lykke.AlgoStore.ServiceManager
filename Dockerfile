FROM java:8
RUN mkdir -p /usr/src/algo
ADD docker-certs   /usr/src/algo/docker-certs
ADD keystore.jks   /usr/src/algo/
RUN chmod 600     /usr/src/algo/keystore.jks

COPY target/algostoremanager-0.0.1-SNAPSHOT.jar /usr/src/algo/algomanager.jar
WORKDIR /usr/src/algo/
EXPOSE 8443
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","algomanager.jar"]