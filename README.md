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

# Deployment

- Install minikube
  - https://minikube.sigs.k8s.io/docs/start/
   
- Build Images
  > docker build -t bookworm:latest -f /eclipse-workspace/BookWorm/src/main/resources/Dockerfile .
  
  > eval $(minikube docker-env)
  
- Deploy Applications
  > kubectl apply -f bookworm.yaml 
  
- For loadbanacer run command below
  > minikube tunnel
  
- Kubernetes Dashboard
  > minikube dasboard
  
- Prometheus&Grafana
  > kubectl create namespace monitoring
  
  > helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
  
  > helm install k8spromethuesstack --namespace monitoring prometheus-community/kube-prometheus-stack

