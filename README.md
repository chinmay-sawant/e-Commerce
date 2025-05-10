# e-Commerce: A Spring Boot Learning Project

## Project Description

This project, "e-Commerce", is a hands-on learning initiative aimed at gaining a deeper understanding of core Spring Boot concepts. It serves as a practical platform to explore and implement various aspects of building a modern web application using the Spring ecosystem, including comprehensive testing practices.

## Features & Concepts Explored

This project focuses on implementing and understanding:

* Building RESTful APIs with Spring Boot.
* Implementing robust security using Spring Security.
* Integrating with Auth0 for JSON Web Token (JWT) based authentication.
* Data persistence using Spring Data JPA.
* Database management with PostgreSQL and Hibernate.
* Setting up Continuous Integration/Continuous Deployment (CI/CD) pipelines using GitHub Actions.
* Implementing unit and integration tests using JUnit 5 and Mockito.

## Tech Stack

* **Language:** Java
* **Framework:** Spring Boot
* **Security:** Spring Security, Auth0 (for JWT)
* **Data Access:** Spring Data JPA, Hibernate
* **Database:** PostgreSQL
* **Testing:** JUnit 5, Mockito
* **CI/CD:** GitHub Actions

## Future Enhancements

The project is planned to be extended with the following integrations:

* **Messaging:** Integration with Kafka for asynchronous communication between services.
* *More planned features to be added...*

## Getting Started

*(Add prerequisites like Java, Maven/Gradle, etc.)*

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/chinmay-sawant/e-Commerce.git](https://github.com/chinmay-sawant/e-Commerce.git)
    cd e-Commerce
    ```

2.  **Database Setup:**
    * Ensure you have PostgreSQL running.
    * By default, the application expects PostgreSQL to be running on `localhost` on port `5432`.
    * If your PostgreSQL instance is running on a different host or port, you will need to update the database connection properties in your `application-*.properties` or `application-*.yml` files accordingly.
    * *(Add any necessary instructions for creating the database or user if needed.)*

3.  **Auth0 Configuration:**
    * Go to the Auth0 website and log in. You can often log in easily using your Google ID, and an account will be created automatically if you don't have one.
    * Navigate to your Auth0 Dashboard.
    * **Create an Application:**
        * Go to Applications -> Applications.
        * Click "Create Application".
        * Select "Regular Web Application".
        * Give your application a name (e.g., "e-Commerce Dev App").
        * Select the technology stack you are using (though this is less critical for API authorization setup).
        * Click "Create".
    * **Create an API:**
        * Go to Applications -> APIs.
        * Click "Create API".
        * Provide a Name and an Audience (a unique identifier URI for your API, e.g., `https://my-ecommerce-api.com`).
        * Click "Create".
    * **Authorize the API in your Application:**
        * Go back to Applications -> Applications and select the Regular Web Application you created earlier.
        * Navigate to the "APIs" tab within your Application settings.
        * Authorize the API you just created by enabling the toggle or clicking "Add Authorized API".
    * Configure your Spring Boot application with your Auth0 Domain and the API Audience you defined. This is typically done in your application's configuration files (`application.properties` or `application.yml`).

4.  **Build and Run:**
    *(Provide commands to build and run the application, e.g., using Maven or Gradle.)*
    ```bash
    # Example using Maven
    mvn clean install
    mvn spring-boot:run
    ```
    or
    ```bash
    # Example using Gradle
    ./gradlew build
    ./gradlew bootRun
    ```

5.  **Access the Application:**
    Once the application is running, it will be available at:
    ```
    http://localhost:8080
    ```

6.  **Access Swagger UI:**
    The API documentation and interactive playground are available at:
    ```
    http://localhost:8080/swagger-ui.html
    ```

7.  **Using Swagger UI with Auth0 Authentication:**
    Since the API is secured with Auth0 JWT, you will need to obtain a token from your Auth0 application.
    * Obtain an access token from your Auth0 tenant (e.g., by following the testing instructions provided in your Auth0 API settings, or using a tool like Postman by configuring it to request a token from your Auth0 authorization server using the client credentials of your application).
    * In the Swagger UI, look for an "Authorize" button or a similar mechanism.
    * Enter your Auth0 access token in the required field (usually labeled "bearerAuth" or similar, in the format `Bearer YOUR_TOKEN_HERE`).
    * You should now be able to make authenticated requests using the Swagger UI.

## Contributing

*(Add information on how others can contribute to your project, if you are open to contributions.)*

## License

*(Specify the license for your project, e.g., MIT, Apache 2.0, etc.)*
