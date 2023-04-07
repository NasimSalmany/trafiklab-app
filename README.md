## Trafiklab API call Spring Boot REST API

Trafiklab API call app is a web service that provides access to find out which Buslines have the most BusStops on
their route, and list the top 10 as clear text. Application also list the names of every
BusStop of the Busline that has the most stops.

## Requirements
To run Trafiklab Application, you will need:

Java 17 or later

## Installation
To install trafiklab-app REST API:

1. Clone the repository from GitHub:
git clone [GitHub Repo](https://github.com/NasimSalmany/trafiklab-app.git)

2. Navigate to the project directory:
cd trafiklab-app

3. Configure the APIKey in the application.properties file:
trafiklab.api.key=API_KEY

## Properties
Set proper APIKey for calling SL Stops and lines v2.0 (Hållplatser och linjer 2) API

````
Set in /resources/application.properties:
trafiklab.api.key=<API_KEY>
````

## Compile the application:
```bash
mvn clean compile
```

## Testing
To test My Spring Boot REST API, you can use a tool like Postman or a test that are implemented.
Unit tests are provided in the src/test/java directory.
```bash
mvn test
```

## Run the application:
```bash
mvn spring-boot:run
```

## Usage
To use My Spring Boot REST API, you can make requests to the following endpoints:

```bash
GET /buslines/most-stops:
```
Returns a list top 10 Buslines have the most BusStops.
To make a request, send an HTTP request to the appropriate endpoint using a tool like cURL or Postman. For example, to retrieve result

## Authentication
This Spring Boot REST API does not require authentication.

## Error handling
If an error occurs while processing a request, My Spring Boot REST API will return an HTTP error code and a JSON error message in the response body. Possible error codes include:

. `429 Too Many Requests`: API usage limit exceeded(5 time per minute).
