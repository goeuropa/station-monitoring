## Getting Started
- Setup environment var. in docker-compose.yml or set it in application.yml if you don't use the Docker.

----------------------------------------------------------
### examples:
##### base-url: https://oba-example-server.org
##### key: "the same key as for OBA API"
##### agency: "agency ID"

----------------------------------------------------------
## Run
Requirement:
- `java 17 + `
- `docker` - optional.

### Commands:

```shell
./gradlew bootRun
```
Optional docker compose:
```shell
./gradlew assemble
```
```shell
docker compose up -d
```

## Check OpenApi Docs
http://localhost:8080/swagger-ui/index.html
