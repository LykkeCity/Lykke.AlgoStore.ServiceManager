Setup AlgoServiceManager


1. Configure your docker-java properties

DOCKER_TLS_VERIFY=1
DOCKER_HOST=tcp://192.168.99.101:2376
DOCKER_CERT_PATH=/opt/docker/...

api.version=1.23
registry.url=https://index.docker.io/v1/
registry.username=myuser
registry.password=mypass
registry.email=email@lykke.com

2. Generate a security keystore and certificate

keytool -genkey -keyalg RSA -keystore keystore.jks -keysize 2048

cp  keystore.jks src/main/resources

3. Edit in application.properties 

server.port=8443
server.ssl.key-store=classpath:keystore.jks
server.ssl.key-store-password=yourKeyStorePass
server.ssl.key-password=your SSL certificate key pass


4. mvn spring-boot:run