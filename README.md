# order-service-kafka-producer


This is a simple spring boot application to demo REST API, Hibernate, JWT, Unit Testing, Kafka Producer in Spring

I have created a Spring Boot microservice which takes in a JSON request payload, creates an order and sends order details to a Kafka topic. The application has the following features:

1. Creation of Spring Boot microservice and persist the data in 2 tables: Order and Order_Details
2. Persist the order in an RDBMS (i have used an in-memory H2 database to save some time as I developed it over the weekend)
3. Service layer is marked as @Transactional to make sure the order creation process respects ACID properties.
4. Spring Data JPA to use some out-of-the-box CRUD operations provided by Spring.
5. JWT is used to add security to the REST API.
6. Incoming request is validated using JSR Bean validation
7. Spring Boot 2.3.1 comes with an out-of-the-box support to dockerize the application
8. Unit Tests for Service and Controller layers
9. Send a message to Kafka topic after order has been created. This can be used in a scenario where order fullfilment and analysis needs to be done once order is created.

To run the application in local(without Docker), please make sure you have:

1. Maven 3.2+
2. Java 8, 9, 10 or 11
3. Kafka 2.5 (https://kafka.apache.org/quickstart)

Steps:

Download the source code in your local
1. Unzip the code and open a terminal at the folder
2. Run the command: mvn spring-boot:run
3. Once the app is up, open a REST API client app like Postman.
4. Call POST method on localhost:8080/authenticate with the following payload: { "username": "iamavaliduser", "password": "password" } Copy the token in the response. We will use it in the next step
5. Call POST method on localhost:8080/orders with the following payload: Header: Key: Authorization Value Bearer <token from step 4>
Sample Body: { "orderAmount": "1000.00", "orderDetails": [ { "itemName": "Robot toy", "itemAmount": "700" }, { "itemName": "Legos", "itemAmount": "300" } ] } Note down the orderId in the response. We will use it in the next step
6. Call GET method on localhost:8080/orders/{orderId from previous step} and Header: Key: Authorization Value Bearer <token from step 4>. You should see the order created in step 4.
7. Run the following command in a terminal window containing Kafka installation: bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic orders --from-beginning
8. You should see the order created in above steps
