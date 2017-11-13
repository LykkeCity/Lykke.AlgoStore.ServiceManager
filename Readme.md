
# Configure docker-java properties
Please configure your docker host url, put your certificates in ./docker-certs and edit your credentials for the dockerhub repo 

```
DOCKER_TLS_VERIFY=1
DOCKER_HOST=tcp://192.168.99.101:2376
DOCKER_CERT_PATH=./docker-certs

api.version=1.23
registry.url=https://index.docker.io/v1/
registry.username=myuser
registry.password=mypass
registry.email=email@lykke.com
```

# Generate a security keystore and certificate
Algostore operates in SSL only mode so you have to create your keystore and to generate your initial key for it. 
```
keytool -genkey -keyalg RSA -keystore keystore.jks -keysize 2048
cp  keystore.jks src/main/resources
```
# Edit application.properties 
In application.properties you have to specify the path to your keystore and the passwords for your keystore and the generated certificate
```
server.port=8443
server.ssl.key-store=classpath:keystore.jks
server.ssl.key-store-password=yourKeyStorePass
server.ssl.key-password=your SSL certificate key pass
```

#Create postgresql database 
```
createdb -h localhost -p 5432 -U postgres algostoremanager
CREATE ROLE algostore LOGIN PASSWORD 'my_password';
GRANT ALL PRIVILEGES ON database algostoremanager TO algostore;
```

#Update your application properties with:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/algostoremanager
spring.datasource.username=algostore
spring.datasource.password=algopass321!
spring.datasource.platform=POSTGRESQL

spring.datasource.initialize=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

```

Note that initially to create the database schema you should set
```
spring.jpa.hibernate.ddl-auto=create
```
If you wish to update the schema do:
```
spring.jpa.hibernate.ddl-auto=update
```
For production use
```
spring.jpa.hibernate.ddl-auto=validate
```
 
 
 
 

# Run
You can run your code through the maven spring boot plugin
```
mvn spring-boot:run
```

# Build a docker image 
```
docker build --tag lykke/algoservicemanager:1.0 .
docker run -dP 8443:8443 --name AlgoServiceManager lykke/algoservicemanager:1.0
```
# Deploy as a service 
```
docker service create --name algostore -p 38443:8443 --replicas 1 algostoremanager:1.1
```
# Update 
```
docker service update --image algostoremanager:1.2 algostore
```
# Authentication 
If you want to enable also user/password authentication 

## Enable web-security by uncomenting 
```
<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
## mvn clean package
## docker build ..
## Finally 
```
docker logs | grep default
```
