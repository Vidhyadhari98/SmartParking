# smart-parking-api

Backend component of smart-parking system which is responsible for providing all configured parking with sensors 
as well as processing bookings for sensors in a parking.

## Tech stack

### Development

    - Java 11
    - Spring Boot 2.7
    - Hibernate 5
    - Mqtt

### Build

    - Maven 3

### Deploy

    - Google cloud platform App Engine

### Database

    - Google cloud SQL

## Build and Tests

- For building locally (without tests)

    - _mvn clean package_

- For running tests locally

    - Comment and uncomment one dependency and few properties in _pom.xml_ and _application.properties_ and then run 
      the maven goal _mvn clean test_

## Deploy

- To deploy the component on Google cloud app engine follow the following steps -

        1. A running instance of Google cloud SQL with SQL admin API enabled
        2. Google cloud sdk installed locally
        3. Firewall opening on tcp port 1883 for MQTT connectivity
        4. Update application.properties with database connection string
        5. Run the command gcloud app deploy
