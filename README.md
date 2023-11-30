# Bookworm

Bookworm is a rest api for an online book store. 


# Tech Stack

Java11,
Spring Boot(web, test, data-mongodb, data-redis, security, log4j2, cache, validation, aop)
REST,
MongoDB,
Spring Security Oauth2,
Redis,
Maven,
Junit5, Mockito, Assertj,
Kubernetes

# Rest API

- UserRegistryController : Restfull Service for user registry
  - signUp : Method for user creation
 
- CustomerController : Restfull Service for customer operations.
  - saveCustomer : Method for customer creation
  - getCustomerOrders : Method for customer's order retrieval

- BookController : Restfull Service for customer operations.
  - saveBook : Method for book creation
  - getBook : Method for customer retrieval
  - updateBookStock : Method for book stock update
 
- OrderController : Restfull Service for order operations.
  - saveOrder :  Method for order creation
  - getOrder :  Method for order retrieval
  - getOrders :  Method for all order retrieval
    
- StatisticsController : Restfull Service for statistics operations.
  - getMonthlyStatistics : Method for monthly statistics retrieval


