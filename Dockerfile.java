FROM java:8
RUN mkdir /usr/src/algoapp
COPY Main.java /usr/src/algoapp
WORKDIR /usr/src/algoapp
RUN javac Main.java
CMD ["java", "Main"]