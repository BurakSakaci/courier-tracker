# Courier Tracker

A Spring Boot application for tracking courier locations and calculating distances to nearby stores.

## Overview

The Courier Tracker application allows tracking of courier locations and provides functionality to:
- Record courier location updates
- Calculate distances between couriers and stores
- Detect when couriers are near stores
- Calculate total distance traveled by couriers
- Calculate last segment distance traveled by couriers
- Record store entrances
- Evict cache

## Features

- **Location Tracking**: Record and store courier locations with timestamps
- **Distance Calculation**: Calculate distances between couriers and stores using pluggable strategies (Haversine, Vincenty, etc.)
- **Store Entry Detection**: Detect when couriers enter the vicinity of a store
- **Automatic Store Loading**: Load store data from a JSON file on application startup

## Project Structure

```
courier-tracker/
├── src/
│   ├── main/
│   │   ├── java/com/sakaci/couriertracking/
│   │   │   ├── config/                  # Configuration classes
│   │   │   │   └── StoreLoader.java     # Loads store data from JSON file
│   │   │   ├── controller/              # REST API controllers
│   │   │   │   └── CourierTrackingController.java
│   │   │   ├── domain/                  # Domain model
│   │   │   │   ├── constants/           # Constants and enums
│   │   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── entity/              # JPA entities
│   │   │   │   │   ├── CourierLocation.java
│   │   │   │   │   └── Store.java
│   │   │   │   └── repository/          # Spring Data JPA repositories
│   │   │   ├── event/                   # Event handling
│   │   │   ├── observer/                # Observer pattern implementations
│   │   │   ├── service/                 # Business logic services
│   │   │   │   ├── CourierLocationService.java
│   │   │   │   ├── DistanceCalculatorService.java
│   │   │   │   ├── StoreEntranceService.java
│   │   │   │   ├── StoreService.java
│   │   │   │   └── impl/                # Service implementations
│   │   │   ├── strategy/                # Distance calculation strategies (Haversine, Vincenty)
│   │   │   └── util/                    # Utility classes
│   │   └── resources/
│   │       ├── application.yml          # Application configuration
│   │       └── stores.json              # Store location data
│   └── test/                            # Test classes
├── .gradle/                             # Gradle cache
├── build/                               # Build output
├── gradle/                              # Gradle wrapper
├── build.gradle                         # Gradle build configuration
├── settings.gradle                      # Gradle settings
└── README.md                            # This file
```

## Technology Stack

- **Java 21**
- **Spring Boot 3.4.5**: Modern, production-grade Spring applications
- **Spring Data JPA**: Simplifies data access with JPA
- **Spring Kafka**: Kafka messaging support
- **Spring Cache (Caffeine, Redis)**: Fast in-memory and distributed caching !!! can be implemented 
- **Flyway**: Database migrations
- **H2 Database**: In-memory DB
- **Lombok**: Reduces boilerplate code
- **Gradle**: Build automation tool
- **Testcontainers**: Integration testing with Kafka/PostgreSQL !!! can be implemented 
- **Unit Tests**: Unit testing
- **Strategy Pattern**: Pluggable distance calculation (Haversine, Vincenty, etc.)
- **Observer Pattern**: Pluggable store entry detection with listeners

## API Endpoints

#### Courier APIs

- `POST /api/courier` : Record a courier's new location
request body:
```json
{
  "courierId": "123e4567-e89b-12d3-a456-426614174000",
  "lat": 40.9923307,
  "lng": 29.1244229
}
```

- `GET /api/courier/total-distance/{courierId}` : Get the total distance traveled by a courier
response body:
```json
{
  "totalDistance": 123456.78
}
```

- `GET /api/courier/last-distance/{courierId}` : Get the last segment distance traveled by a courier
response body:
```json
{
  "lastDistance": 123456.78
}
```
- `POST /api/courier/store-entrance` : Record a courier's store entrance
request body:
```json
{
  "courierId": "123e4567-e89b-12d3-a456-426614174000",
  "storeId": "123e4567-e89b-12d3-a456-426614174000"
}
```

- `POST /api/courier/evict-cache` : Evict the cache

#### Distance Calculation APIs

- `GET /api/distance-calculation/strategies` : List available distance calculation strategies
response body:
```json
{
  "strategies": ["haversine", "vincenty"]
}
```
- `POST /api/distance-calculation/strategy/{name}` : Set the active distance calculation strategy (e.g., haversine, vincenty)
request body:
```json
{
  "strategy": "haversine"
}
```

### Actuator Endpoints
- `/actuator/health` : Health check
- `/actuator/metrics` : Application metrics


## Data Models

### CourierLocation
- `id`: Unique identifier (UUID)
- `courierId`: Courier identifier
- `lat`: Latitude
- `lng`: Longitude
- `timestamp`: Time when the location was recorded
- `isStoreEntry`: Flag indicating if the courier is near a store

### Store
- `id`: Unique identifier (UUID)
- `name`: Store name
- `lat`: Latitude
- `lng`: Longitude
- `storeType`: Type of store

### StoreEntrance
- `id`: Unique identifier (UUID)
- `courierId`: Courier identifier
- `storeId`: Store identifier (UUID)
- `entranceTime`: Time when the courier entered the store

## Getting Started

### Prerequisites
- Java 21 (ensure `JAVA_HOME` points to a Java 21 installation)
- Gradle (wrapper included)

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Start dependencies if needed (Kafka, Redis; e.g. via Docker Compose)
4. Don't forget to check stores.json, because it loads store data from this file on application startup
5. Run the application:
   ```sh
   ./gradlew bootRun
   ```
   The app will start on port 8080 by default.

### Building the Application
```sh
./gradlew build
```

### Running Tests
```sh
./gradlew test
```
Tests use JUnit, Instancio and Mockito.

You can view test coverage report at `build/reports/jacoco/test/html/index.html`. After running tests, you can view the report. ./gradlew clean test jacocoTestReport 


### Accessing the H2 Console
- Visit [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (enabled by default in dev)
- JDBC URL: `jdbc:h2:mem:courierdb;DB_CLOSE_DELAY=-1`
- User: `admin` | Password: `admin`

## Configuration

Configuration is managed via `src/main/resources/application.yml` and environment variables.

### Main Configuration Options

- **Database**
  - `spring.datasource.url` (default: `jdbc:h2:mem:courierdb;DB_CLOSE_DELAY=-1`)
  - `spring.datasource.driver-class-name` (default: `org.h2.Driver`)
  - `spring.datasource.username` / `spring.datasource.password` (default: `admin`/`admin`)
  - For PostgreSQL, set `DATASOURCE_URL`, `DATASOURCE_DRIVER`, etc.
- **JPA**
  - `spring.jpa.hibernate.ddl-auto` (default: `validate`)
  - `spring.jpa.show-sql` (default: `true`)
- **H2 Console**
  - `spring.h2.console.enabled` (default: `true`)
  - `spring.h2.console.path` (default: `/h2-console`)
- **Kafka**
  - `spring.kafka.bootstrap-servers` (default: `localhost:29092`)
  - Consumer/producer/serialization settings are configurable via env vars (see `application.yml`)
- **Redis**
  - `spring.data.redis.host` (default: `localhost`)
  - `spring.data.redis.port` (default: `6379`)
- **Cache**
  - `spring.cache.type` (default: `caffeine`)
  - `spring.cache.caffeine.spec` (default: `maximumSize=500,expireAfterWrite=1h`)
- **Application**
  - `app.store-data-file` (default: `classpath:stores.json`)
  - `app.proximity-threshold-meters` (default: `100`)
  - `app.reentry-minutes-threshold` (default: `1`)
  - `app.kafka.topics.location-updates` (default: `courier-locations`)
  - `app.kafka.topics.store-entrances` (default: `store-entrance-events`)
- **Actuator**
  - `/actuator/health`, `/actuator/info`, `/actuator/metrics`, `/actuator/kafka` enabled
- **Server**
  - `server.port` (default: `8080`)

All properties can be overridden via environment variables (see `${...}` syntax in `application.yml`).

### Mock Coordinates

You can check for mock_coordinates at `src/main/resources/mock_coordinates` to test manually.