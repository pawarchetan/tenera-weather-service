# tenera-weather-service

#### Problem Statement:
* https://www.notion.so/Tenera-Backend-Coding-Challenge-f5a4c891e1094717b224d9d1ad268a88

#### Solution:
* Implement REST API to search for weather information by location
* Implement REST API to search for weather information history by location (latest 5 records)

#### Technology used :
* Java8
* JUnit
* SpringBoot
* H2 DB
* Gradle
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
* Above 95%, most of the code has been covered (measured using Intellij IDEA plugin)

#### API Documentation:
* Swagger has been used to document api. Swagger is accessible at: http://localhost:8080/swagger-ui.html
