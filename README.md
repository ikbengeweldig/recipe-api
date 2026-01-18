# Recipe API

## Running the application in production mode

To run the application using the production profile, you must first build a Docker image from the application JAR.

### 1. Build the Docker image

From the root of the project, execute:

```bash
mvn clean verify
```

```bash
mvn jib:dockerBuild
```

```bash
docker compose --profile prod down -v
```

```bash
docker compose --profile prod up -d
```

## Future Improvements

- Add Spring Boot Actuator for application monitoring and health endpoints

- Add metrics collection and observability integration

- Improve logging configuration and structured logging

- Add performance and load testing setup

- Extend integration test coverage

- Add CI/CD pipeline automation