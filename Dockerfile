FROM java:8
RUN mkdir -p /usr/src/algo
COPY docker-certs   /usr/src/algo/
COPY target/algostoremanager-0.0.1-SNAPSHOT.jar /usr/src/algo/algomanager.jar
WORKDIR /usr/src/algo/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","algomanager.jar"]