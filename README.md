This is a  Spring Boot application designed to securely store user passwords and notes in an encrypted format. The application comprises two microservices, namely "Authentication" and "Password-Saving-Service." 

The authentication microservice is responsible for all authentication logic, using Spring Security for authentication and authorization. It utilizes the nimbus jose library for JWT operations and provides different user authorities, such as "Free" and "Premium." While "Free" users can store passwords, "Premium" users can also save notes.
The authentication microservice validates API requests with a validator and sends them to the password-saving-service via Kafka. Passwords or notes for a specific user can be retrieved using "Open Feign" requests. This  application leverages "@retry" and "@circuitbreaker" from the "resilience4j" library to improve resilience. 
User information and roles are stored in a MySQL database using "OneToMany" mapping. 

The password-saving-service microservice is responsible for securely storing user passwords and notes. It encrypts data using the Jasypt library in AES-256 format and caches passwords in Redis. 
Both microservices feature custom exception handling to ensure a robust and efficient application. 

Overall, this Spring Boot application offers a secure and reliable way to store user passwords and notes.

 
