# Kong

Kong is a cloud-native, fast, scalable, and distributed Microservice Abstraction Layer (also known as an API Gateway, API Middleware or in some cases Service Mesh).

## Kong Docker Installation
1 - Create a docker network — this network will be use for kong and our API server
```bash
$ docker network create kong-net
```


2-Start database for Kong — there is two option database : Postgres or Cassandra. For know we use postgres.
```bash
$ docker run -d --name kong-database \
--network=kong-net \
-p 5555:5432 \
-e “POSTGRES_USER=kong” \
-e “POSTGRES_DB=kong” \
postgres:9.6

```

3 — Prepare database, run migration with Kong container:
```bash
$ docker run --rm \
--network=kong-net \
-e “KONG_DATABASE=postgres” \
-e “KONG_PG_HOST=kong-database” \
kong:latest kong migrations bootstrap

```
4 — Start the Kong, when the migrations have run and your database is ready, start a kong container.
```bash
$ docker run -d --name kong \
--network=kong-net \
-e “KONG_LOG_LEVEL=debug” \
-e “KONG_DATABASE=postgres” \
-e “KONG_PG_HOST=kong-database” \
-e “KONG_PROXY_ACCESS_LOG=/dev/stdout” \
-e “KONG_ADMIN_ACCESS_LOG=/dev/stdout” \
-e “KONG_PROXY_ERROR_LOG=/dev/stderr” \
-e “KONG_ADMIN_ERROR_LOG=/dev/stderr” \
-e “KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl” \
-p 9000:8000 \
-p 9443:8443 \
-p 9001:8001 \
-p 9444:8444 \
kong:latest


```
5 -Check Kong Instance.
```bash
$ curl -i http://192.168.99.100:9001
```

## Create Docker Image of Micro-Services
```bash
gradle build

Docker build -t customer -f Dockerfile .
Docker build -t transaction -f Dockerfile .

```

## Run Docker Image of Micro-Services in Kong Network(Docker network)
```bash

docker run -d --name=customer --network=kong-net customer
docker run -d --name=transaction --network=kong-net transaction

```
## Command to check all container in docker kong network
```bash
  docker network inspect kong-net

```



## Services,Routes,Plugins,Consumers
                         https://docs.konghq.com/0.13.x/getting-started/configuring-a-service/
                         https://github.com/faren/NodeJS-API-KONG/blob/master/Kong.postman_collection.json
                         
