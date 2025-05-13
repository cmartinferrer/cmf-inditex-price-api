# 💰 CMF Inditex Price API

This project implements a service to manage and retrieve prices based on a set of conditions. It is built using Spring Boot, Docker, and follows clean architecture principles (Hexagonal Architecture). The service exposes REST APIs to retrieve the applicable price for a given product, brand, and application date.

## 🧠 Design Decisions

1. **Hexagonal Architecture**: The application follows the Hexagonal Architecture (also known as Ports and Adapters). This allows for better separation of concerns, easier testing, and greater flexibility in changing the underlying implementation (e.g., switching databases, APIs, etc.).

2. **REST API**: The service exposes a REST API to retrieve the applicable price for a given product, brand, and application date.

3. **API First:** The controller was developed following an API First approach. The API [contract](https://github.com/cmartinferrer/cmf-inditex-price-api/blob/main/src/main/resources/openapi/openapi.yaml) was defined upfront using OpenAPI, and the implementation was aligned to that specification. This ensures consistency between documentation and implementation, facilitates collaboration across teams (backend, frontend, QA), and reduces integration issues.

4. **Database**: We are using H2 as an in-memory database for development and testing purposes. It allows us to quickly run tests and verify the application without having to set up an external database.

5. **Docker**: The project is dockerized to provide an easy-to-deploy solution in any environment. It ensures consistency across different machines and simplifies the setup process for developers.

6. **Testing**: Unit tests are written following the **Given-When-Then** structure with **JUnit 5**. Integration tests are performed using MockMvc for testing the REST endpoints.

7. **Swagger**: Swagger is used to generate and display API documentation. It provides an interactive interface to test the endpoints directly in the browser.

8. **Data Mapping**: We use **MapStruct** to map between the domain model (`Price`) and the entity model (`PriceEntity`). This reduces boilerplate code and ensures a clean separation between layers.

## 🛠️ Prerequisites

1. **Docker**: Ensure Docker Desktop is installed and running on your machine. You can download it from [here](https://www.docker.com/products/docker-desktop).

2. **Java 17**: The project uses JDK 17. If you're building locally (without Docker), ensure you have Java 17 installed.

3. **Maven**: The project uses Maven for building and managing dependencies. Maven should be installed if you're not using Docker to build the project.

## 🚀 Setup Instructions
Clone the repository:
```bash
   git clone https://github.com/cmartinferrer/cmf-inditex-price-api.git
   cd cmf-inditex-price-api
```

### 🐳 Execute with Docker
Build the Docker images and start the containers with Docker Compose:
```bash
  docker-compose up --build
```
This will:
- Build the application image and the database image. (currently h2 in memory)
- Start both the application and the database containers.
- Expose the application on port 8080.

### ⚙️ Execute without Docker (Optional)
If you prefer to run the application without Docker, ensure you have Java 17 and Maven installed. Then, run the following commands:
```bash
  ./mvnw clean package
  java -jar target/price-service.jar
```

## 📡 Application Endpoints
The following are the key endpoints exposed by the application:

- GET /prices
  - Retrieves the price information for a specific product and brand, at a specific date.
  - Parameters:
    - **applicationDate:** The date when the price is to be applied (e.g., "2020-06-14T10:00:00").
    - **productId:** The ID of the product.
    - **brandId:** The ID of the brand.
  - **Response:** A PriceResponse object with the price details for the given product, brand, and application date.

### 📖 Swagger UI
The API documentation is automatically generated using Swagger. You can view and interact with the API at:
- Swagger UI: http://localhost:8080/swagger-ui/index.html

This page provides a comprehensive view of all available endpoints and allows you to test them directly.

### 🗃️ H2 Console
You can access the H2 console to inspect the in-memory database at:
- H2 Console: http://localhost:8080/h2-console

The connection URL will be automatically configured to **jdbc:h2:mem:testdb**. The username and password are both sa.

## 🗄️ Database Schema
The application uses an in-memory H2 database for storing price information. The schema for the prices table is as follows:
```sql
CREATE TABLE IF NOT EXISTS prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price_list INT NOT NULL,
    product_id BIGINT NOT NULL,
    priority INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL
);
```
Sample data is included in the data.sql file to populate the database upon application startup.

## 🧪 Test Coverage

- **Unit Tests:** Written using **JUnit 5** following the **Given-When-Then** structure. All core components are unit tested in isolation using **Mockito** for mocking dependencies.
- **Integration Tests:** Implemented using **Spring's MockMvc** to simulate HTTP requests and verify the behavior of the REST API endpoints, including response structure, status codes, and input validation.
- **Mutation Tests:** Conducted using **PIT (Pitest)** with the **JUnit 5 plugin**, targeting the application service layer. Mutation testing helps ensure that your test suite effectively detects unexpected code changes by introducing controlled faults into the code and verifying that tests fail as expected.

## ▶️ Running Tests

To run all tests locally, including mutation testing:
```bash
# Run unit and integration tests
mvn clean test

# Run mutation tests
mvn org.pitest:pitest-maven:mutationCoverage
```
This will run all the tests and generate a test report in the target directory and Pi Test Coverage Report in 'target/pit-reports/index.html' 

## 🧪 Postman Tests

This project includes a comprehensive set of Postman tests to validate the functionality of the REST API endpoints. The tests ensure that the API behaves as expected by checking the response status, content, and data integrity.

### ✅ Tests Included:
- **GET /prices**: This endpoint returns price information based on the given `productId`, `brandId`, and `applicationDate`. The tests validate the following fields in the response:
  - **priceList**: Validates that the `priceList` is a number and is greater than 0.
  - **price**: Ensures that the `price` is a valid number and greater than 0.
  
### 🧭 Running Postman Tests

1. **Import the Postman Collection:**
  - Download the [Postman collection](https://github.com/cmartinferrer/cmf-inditex-price-api/blob/main/src/main/resources/postman/cmf-inditex-price-api.postman_collection.json) file provided or import it directly into Postman.

2. **Execute the Tests:**
  - Open the collection in Postman and click on the "Run" button to execute all tests in the collection.
  - Postman will run each request and validate the tests configured for each endpoint.

3. **View the Test Results:**
  - After executing the requests, you can view the results for each test in the "Test Results" tab.
  - If any test fails, you will see an error message indicating which validation failed and the response details.


## ✅ Conclusion
This project provides a robust and scalable solution for retrieving price information based on various conditions. The use of Docker, clean architecture, and testing practices ensures a smooth development and deployment experience.

If you have any questions or need further assistance, feel free to reach out.