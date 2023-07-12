# Planning Poker Service

## Technologies

- Spring Boot 3.0.6
- Spring Framework 6.0
- Java 17.0.2
- Maven 3.8.7
- MySQL 8.0
- Docker 20.10.17

## Setup

Create a .env file:
```shell
cp .env.example .env
```

Set the database environment variables:
```text
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=planningpoker
MYSQL_USER=springuser
MYSQL_PASSWORD=springpass
```

Start the MySQL Server:
```shell
docker-compose up -d mysql
```

## Demo

Start the Demo Application:
```shell
docker-compose up -d
```

Stop the Demo Application:
```shell
docker-compose down -v
```

## Development

Start the Application:
```shell
mvn spring-boot:run
```

## Links
- [Swagger UI](http://localhost:8080/swagger-ui/index.html#/)
- [Adminer](http://localhost:8085)
