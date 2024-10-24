# README #

I tried to create a service as simple as possible in order to reduce external libraries and use Spring potential.

### How to run ###

class com.bcnc.prices.PricesApplication can be run from java: 
```
java com.bcnc.prices.PricesApplication
```

## Architecture ##

The project is built with maven. Run to build and run test:
```
mvn clean install
```
The project is separated in layers: web -> domain -> database
- Web
  - Contains the @RestController responsible for MVC and open API.
  - Receive and validate the requests
  - Call to domain layer
  - Manage the error response when there are exceptions with ControllerAdvice.
- Domain
  - Contains the @Service classes.
  - It can't see the web layer.
  - Call to database and manage the data to response.
- Database
  - Contains the @Repository and @Entity to manage the database with JPA.
  - It can't see the domain layer.
  - Manage database connections and querys.

Each layer contains they own model to isolate the implementation and definition. IMPORTANT: I have kept the column names like the example to show that DTO model do not have to be the same as DAO.
- Web model: it uses validation and swagger annotation to define the DTO model.
- Domain: is used to transform the data received from database.
- Database: it uses the persistence annotations to manage database data and DAO model.

#### External libraries ####

I have used external libraries with a solid background and community behind. They are developed thinking to be integrated in Spring:
- [mapstruct](https://mapstruct.org/)
- [micrometer](https://micrometer.io/)

#### Traceability ####

Spring actuator and micrometer are activated to add traceId and spanId to the logs.

#### AOP ####

It's used to manage logging.
- I created a LogAspect and the annotation LogTime to log the execution time for every method with the annotation.
- I created a ControllerAspect to log the request, response and execution time for every endpoint in a controller.

#### Mapstruct ####

It's an external library to simplify mapping conversion between classes

#### TODO Improvements ####

- The project name and some endpoint must be reviewed. Right now we call to **PriceController** and endpoint **/prices** to get a **ProductPriceRs** from **Rates** table. It is confusing for the developer.
- The endpoint created in PriceController is a POST for comfort. It could be a GET and pass the params with @RequestParam or @PathVariable.
- I can use Maven modules to assure the isolation between layers. 
  ```
  <modules>
    <module>bcnc-group-service-database</module>
    <module>bcnc-group-service-domain</module>
    <module>bcnc-group-service-web</module>
  </modules>
  ```
- Add authentication
- Add end2end test
- ControllerAspect:
  - add request logging for HttpMethods different of POST
  - Request or response could have a big size. Do not have to be logged each time.
- If the service receives a request from another API, check the headers to copy spanId and traceId
- Dockerization
  
### Sonar Issues ###

Vulnerabilities found in:

- spring-boot-starter-web
- spring-boot-starter-test
- spring-cloud-starter-sleuth

### TESTS ###

I created different tests for different targets
- WebMvcTest: to test controllers (web layer). 
- DataJpaTest: to test database access.
- Test/ParameterizedTest: to test domain functionality.
- SpringBootTest: to create integration test.

### Commits ###

The idea is make small and simple commits to increase the value

- Initial commit (Version: 0.0.1-SNAPSHOT)
  - Generate project from https://start.spring.io/
  - Create initial README.md
  - Define .gitignore
- API First (Version: 1.0.0)
  - Create controller with generic response
  - Define DTO models
  - Define error response model
  - Add model validation and Exception handler
  - Add OpenApi configuration and annotations
  - Add MVC tests
- Build H2 database (Version: 1.1.0)
  - Add dependencies
  - Add configuration in application.properties
  - Create entity and repository
  - Insert data on startup
  - Add JPA test (Not necessary, only to check is loaded)
- Create business logic (Version: 2.0.0)
  - Add NotFoundException
  - Add "findRatesByBrandIdAndProductId" query
  - Add converters between layers with mapstruct
  - Add domain model
  - Connect layers
  - Add and modify tests
- Add observability (Version: 2.1.0)
  - Create aspects for logging
  - Add traceId and spanId to logs