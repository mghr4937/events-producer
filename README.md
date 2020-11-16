# events-producer
Springboot and Kafka app

POST WITH-NULL-LIBRARY-EVENT-ID
---------------------
curl -i \
-d '{"id":null,"book":{"id":546,"name":"Kafka Using Spring Boot","author":"Dilip"}}' \
-H "Content-Type: application/json" \
-X POST http://localhost:8080/v1/libraryEvent


PUT WITH ID
---------------------
curl -i \
-d '{"id":1,"book":{"id":546,"name":"Kafka Using Spring Boot 55.0","author":"Dil441ip"}}' \
-H "Content-Type: application/json" \
-X PUT http://localhost:8080/v1/libraryEvent

curl -i \
-d '{"id":999,"book":{"id":546,"name":"Kafka Using Spring Boot","author":"Dilip"}}' \
-H "Content-Type: application/json" \
-X PUT http://localhost:8080/v1/libraryEvent

PUT WITHOUT ID
---------------------
curl -i \
-d '{"id":null,"book":{"id":456,"name":"Kafka Using Spring Boot","author":"Dilip"}}' \
-H "Content-Type: application/json" \
-X PUT http://localhost:8080/v1/libraryEvent
