# User API Dev Guide

## Building
To build the prod version of the application:
```
./gradlew bootJar
```
This will create a userapi-'version'-SNAPSHOT.jar file in build/libs.

## Testing
The testing environment uses the in memory H2 database for faster test runs.
In the future it might be better to use the same database as what we're using in prod via test containers.\
To run the tests:
```
./gradlew test
```

## Deploying
The userapi-'version'-SNAPSHOT.jar file can be run on our servers by:
```
java -jar userapi-'version'-SNAPSHOT.jar
```

## Additional Information
- There should be a postgres database running before starting this application
  - The postgres creds is in the application.properties file
