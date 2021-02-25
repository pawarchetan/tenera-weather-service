# tenera-weather-service

#### Problem Statement:
* https://www.notion.so/Tenera-Backend-Coding-Challenge-f5a4c891e1094717b224d9d1ad268a88

#### Solution:
* Implement REST API to search for weather information by location
* Implement REST API to search for weather information history by location (latest 5 records)

#### Technology used :
* Java
* JUnit
* SpringBoot
* H2 DB
* Gradle
* Lombok
* IDE used --> IntelliJ

#### High Level Design decisions:
* REST API for searching weather information by location
  * API internally calls external api to get weather information. Below fields get extracted from api response.
    * name
    * temp
    * pressure
    * main
  * API persist city details in city table which includes name came from external API
  * API persist location/search query details in SearchQuery table and refers it to City table. It is intentionally done to get history
  for given search criteria.
* REST API to get weather information history
  * API searches city by location query
  * After getting city, API queries history by city object
  * Search query to city mapping is done intentionally to get same results for different different combination of same location query. 
  for example: Berlin, Berlin DE, berlin DE will return same history.
* Their is no interface declarations for services as I dont see any scope for it.
* None of the domain entity is exposed directly to outside services. Its exposed only through respective service implementation. For example: City, SearchQuery, WeatherHistory
* `WeatherService.java:` Its sort of facade for api's that we have implemented. Its responsibility is to call their party api. It also responsible for calling other services and collecting data from them.

#### Tables:
* City

Column Name  | Column Type | Index
------------- | ------------- | -------
Id  | bigint |  Yes 
Name  |   varchar(255) | 

* SearchQuery

Column Name  | Column Type | Index
------------- | ------------- | -------
Id  | bigint |  Yes 
Name  |   varchar(255) | Yes
City_Id | bigint | 

* Weather History

Column Name  | Column Type | Index
------------- | ------------- | -------
Id  | bigint |  Yes 
Created_At  |   timestamp | Yes
Pressure | double | 
Temperature | double |
Umbrella | boolean | 
City_Id | bigint |

#### Code coverage:
* Above 90%, most of the code has been covered (measured using Intellij IDEA plugin)

#### API Documentation:
* Swagger has been used to document api. Swagger API is accessible at: http://localhost:8080/swagger-ui.html

#### Future Scope:
1. unit tests for controller (currently integration tests are written)
2. more negative unit tests 

## Give us an indication of what you think a production ready micro service should look like.
I will divide answer into 3 different sections.
* `Production ready code`:\
I feel production ready code should adhere following properties/characteristics:
    1. Readability
    2. Maintainability
    3. Error-handling
    3. Testability
* `Production ready microservice`:
I feel production ready microservice should adhere 12 factor app characteristics which are mentioned here: https://12factor.net/
* `Design considerations`:
    1. Each microservice should be loosly coupled from each other
    2. Microservice should follow singe store per service pattern. 
    3. Each microservice call should be authenticated/authorized before giving access to data
    4. Proper tracing mechanism implemented for service to service calls to debug issues
    5. Monitoring for microservice
    6. Graceful shutdown for microservice
    7. Containerised microservice container
    
## Describe how you would deploy the application in an AWS environment (we are using Terraform).
Assumptions: 
1. application is containerized/dockerized
2. We will be deploying application in EKS using terraform and helm

Procedure/steps:
* Setup terraform
    1. Create state file using `terraform init` command
    2. To see a detailed outline of the changes Terraform would make, run plan. This should include the EKS cluster, VPC, and other AWS resources 
    that will be facilitated in this project: `terraform plan`
    3. If everything looks okay, run `terraform apply` to apply configuration
* Setup kubectl/EKS cluster
    1. Install kubectl
    2. Add `ConfigMap` to the K8S cluster using terraform.
    3. After step 2, we will see nodes coming-in in cluster.
* Helm:
    1. Prerequisite for this to have `ServiceAccount` to allow helm talk to the cluster.
    2. Install helm
* App deployment
    1. We can Terraform template to create helm charts
    2. create helm charts and then apply/create it using `terraform apply` command
 
Why we used Terraform and Helm both? : 
1. We can setup K8S cluster with additional resources like nginx ingress, external dns if required
2. We can deploy K8S application using values from resources provisioned outside of K8S such as S3

