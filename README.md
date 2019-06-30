[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Producer and consumer

Producer and Consumer application using RabbitMQ as `Message Broker`.

### Running

First of all, you should have a `Docker RabbitMQ container` running: 

```
docker-compose up
```

After, set your java version to **10.x.x** and: 

```
./gradlew clean build bootrun
```

### Environment

- Java 10.0.2
- Gradle 4.7+
- Spring boot 2.0.3

### License
Apache License. [Click here for more information.](LICENSE)