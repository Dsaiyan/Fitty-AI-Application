 # Fitty-AI Application
    This project is a microservices-based application designed to provide AI-powered fitness tracking and recommendations. 
    The architecture is built using Spring Boot and leverages various cloud-native patterns for resilience, scalability, and maintainability.
     
 ## Architecture Overview
    The application follows a microservices architecture, with each service responsible for a specific business domain. 
    Communication between services is handled both synchronously and asynchronously.
    
**Service Discovery:** The `discovery-server` (using Netflix Eureka) allows services to register and discover each other dynamically.
**Configuration:** The `config-server` (using Spring Cloud Config) provides centralized configuration management for all microservices.
**API Gateway:** The `api-gateway` acts as the single entry point for all client requests. It handles routing, load balancing, and security.
**Security:** Authentication and authorization are managed by Keycloak, which acts as the central authentication server, issuing JSON Web Tokens (JWTs). The `api-gateway` secures endpoints and validates JWTs on incoming requests.
   
### Communication
  
**Synchronous Communication:** Services communicate with each other using FeignClient, a declarative REST client, for direct HTTP requests.
**Asynchronous Communication:** Apache Kafka is used as a message broker for event-driven communication between services. This decouples services and improves fault tolerance. For example, the `keycloak-kafka-event-provider` publishes authentication events to Kafka topics, which are then consumed by other services like the `activity-service`.

## Microservices
Here is a list of the microservices in the application:
| Service            | Description
| -------------------|-------------------------------------------------------------------------------|
| `activity-service` | Manages user activities, such as workouts and exercises. It consumes events from Kafka to stay in sync with user data.
| `ai-service`       | Provides AI-powered features, such as personalized workout recommendations and progress analysis.
| `api-gateway`      | The single entry point for all incoming traffic. It routes requests to the appropriate microservice and handles cross-cutting concerns.
| `auth-service`     | Integrates with Keycloak to handle user registration and authentication. This service is basically an extention as an user profile service.
| `config-server`    | Provides centralized configuration for all microservices.
| `discovery-server` | Allows microservices to register and discover each other.

## Data Storage 
The application uses a combination of databases to store data:
 
-**PostgreSQL:** A relational database used by services that require structured data and transactional integrity. It is run as a Docker container.
-**MongoDB Atlas:** A cloud-hosted NoSQL database used by services that require flexible data models and scalability, such as the `activity-service` and `ai-service`.

## Infrastructure 
The following technologies are used to support the application:

-**Docker:** Used to containerize and run the databases (PostgreSQL), Kafka, and Keycloak.
-**Keycloak:** The open-source identity and access management solution used for authentication and authorization.
-**Apache Kafka:** A distributed event streaming platform used for asynchronous communication between microservices.
 
## Environment Variables
 
Each microservice has its own `application.properties` or `application.yml` file where environment-specific variables are configured.
These include: 
-Database connection strings
-Kafka broker URLs
-Keycloak server information
-Service-specific settings

To run the application, you will need to configure these variables for your local environment.
