# Recipe API

## Running the application in production mode

To run the application using the production profile, you must first build a Docker image from the application JAR.

### 1. Build the Docker image

From the root of the project, execute:

```bash
mvn clean verify && mvn jib:dockerBuild && docker compose --profile prod up -d
