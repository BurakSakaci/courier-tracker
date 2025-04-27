# Courier Tracker

A Spring Boot application for tracking courier locations and calculating distances to nearby stores.

## Overview

The Courier Tracker application allows tracking of courier locations and provides functionality to:
- Record courier location updates
- Calculate distances between couriers and stores
- Detect when couriers are near stores

## Features

- **Location Tracking**: Record and store courier locations with timestamps
- **Distance Calculation**: Calculate distances between couriers and stores using the Haversine formula
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
│   │   │   │   ├── CourierTrackingService.java
│   │   │   │   ├── CourierTrackingServiceImpl.java
│   │   │   │   ├── DistanceCalculatorService.java
│   │   │   │   └── DistanceCalculatorServiceImpl.java
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

- **Java 17+**
- **Spring Boot**: Framework for creating stand-alone, production-grade Spring applications
- **Spring Data JPA**: Simplifies data access with JPA
- **Lombok**: Reduces boilerplate code
- **Gradle**: Build automation tool
- **H2 Database**: In-memory database (configurable)

## API Endpoints

- `GET /api/courier/distance/{id}`: Get distance information for a courier
- `POST /api/courier/locations`: Update courier location

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
- `storeType`: Type of store (MIGROS, MACRO_CENTER, MION, OTHER)

## Getting Started

### Prerequisites
- Java 17 or higher
- Gradle

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```
   ./gradlew bootRun
   ```
4. The application will start on port 8080 (default)

### Building the Application
```
./gradlew build
```

## Configuration

Configuration is managed through `application.yml`. Key configurations include:
- Database settings
- Store data file location

## License

[Add your license information here]
