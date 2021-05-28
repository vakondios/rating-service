# Rating service
Execute mvn clean install -Pdev, to build and run the unit tests

Execute mvn clean install -Pstaging, to build and run the integration tests. On the database, the system creates 1000 records for testing purposes.

On the application.properties, update the key 'ci2.srvhost' according to your network configuration

**Swagger Access**

http://192.168.31.206:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/ratings-controller/getOverallRating

**Actuator Access**

http://192.168.31.206:8080/actuator

**Eureka Client** has configured, but it is not enable (change on the properties)

**Rest API** is secured with Basic Authentication.

**Ratings Controller**

POST /ratings

GET /ratings/{rated entity}

**Comments**

v.1.0.0