# worldweather
API to Check World Weather

## Overview
worldweather is a small Spring Boot microservice that queries the OpenWeatherMap current weather API for one to three cities and returns structured weather data. It also provides an endpoint to transform that data into simple logs and persist them into a repository.

This README documents the service endpoints, configuration, build & test instructions and examples.

## Requirements
- Java 8+ (project uses target compatible with typical Spring Boot setups)
- Maven (to build and run tests)
- Internet access (when running against the real OpenWeatherMap API)

## Build
From the project root run:

```powershell
mvn clean package
```

This produces a runnable jar in `target/` (for example: `target/worldweather-0.0.1-SNAPSHOT.jar`).

## Run
You can run the application with:

```powershell
java -jar target/worldweather-0.0.1-SNAPSHOT.jar
```

By default the application runs on port 8080 (Spring Boot default). You can change the port using the usual Spring Boot properties (for example `--server.port=9090`).

## Configuration
The application queries OpenWeatherMap's current weather API. The base URL is defined in the code at `com.info.worldweather.constant.WeatherAPIConstants.WS_URL` and points to:

http://api.openweathermap.org/data/2.5/weather

You must pass your OpenWeatherMap API key (APPID) to the endpoints via the `appID` HTTP header. The service will append `?q={city}&APPID={appID}` when calling the upstream API.

Note: For tests the `RestTemplate` used by the service is injected and can be mocked so unit tests won't call the real API.

## REST API Endpoints
Base path: `/v1/world/weather`

1) GET /v1/world/weather/
- Description: Get current weather information for up to three cities.
- Method: GET
- Required headers:
  - `appID`: your OpenWeatherMap API key
- Query parameters:
  - `cityName1` (required): first city name (e.g. London)
  - `cityName2` (optional): second city name
  - `cityName3` (optional): third city name
- Response: JSON array of CityWeather objects (model in `com.info.worldweather.model`).

Example using curl (replace {API_KEY}):

```powershell
curl -G "http://localhost:8080/v1/world/weather/" -H "appID: {API_KEY}" --data-urlencode "cityName1=London" --data-urlencode "cityName2=Paris"
```

2) POST /v1/world/weather/actual
- Description: Fetch current weather for up to three cities, convert results into simple WeatherLog objects and save them into the repository.
- Method: POST
- Required headers:
  - `appID`: your OpenWeatherMap API key
- Request parameters (x-www-form-urlencoded / query string): same as the GET above (`cityName1`, `cityName2`, `cityName3` — `cityName1` is required)
- Response: JSON array of WeatherLog objects (each log contains: location, actualWeather, responseId, temperature).

Example using curl:

```powershell
curl -X POST "http://localhost:8080/v1/world/weather/actual" -H "appID: {API_KEY}" -d "cityName1=London" -d "cityName2=Berlin"
```

## Models
Key model classes (in `com.info.worldweather.model`):
- CityWeather: represents the JSON response from OpenWeatherMap for a city (name, main.temperature, weather[])
- WeatherLog: reduced object produced by the `/actual` endpoint (location, actualWeather, responseId, temperature)

Inspect the `model` package if you need detailed field names for JSON parsing or response inspection.

## Testing
Unit tests are implemented under `src/test/java`. They mock `RestTemplate` to avoid calling the real OpenWeatherMap API. To run the tests use:

```powershell
mvn test
```

If you see failures related to HTTP 401 during tests, ensure tests are using the injected `RestTemplate` rather than creating new instances. The code is structured so `RestTemplate` is injected into `WeatherAPIImpl` and can be mocked by tests.

## Developer Notes
- The controller `com.info.worldweather.controller.WeatherAPI` exposes the endpoints and delegates business logic to `WeatherAPIImpl`.
- `WeatherAPIImpl` builds the upstream request using `WeatherAPIConstants.WS_URL` and appends `?q={city}&APPID={appID}`.
- The `actual` endpoint persists WeatherLog entries through `WeatherLogReposity.saveAll(...)`.
- The application includes Swagger annotations — when running the app you can add Swagger UI to the project or inspect generated API docs if Swagger UI is configured.

## Improvements and TODOs
- Make the upstream base URL and API key source configurable via `application.properties` or environment variables instead of hard-coding in constants.
- Add more robust error handling for upstream failures (timeouts, 4xx/5xx responses) and return meaningful HTTP statuses to clients.
- Add integration tests that mock the upstream API (e.g., with WireMock) to validate end-to-end behavior.

## License
This project repository does not include a license file. Add a LICENSE file if you intend to make the project public.
