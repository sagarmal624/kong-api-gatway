# Kong
Kong is an API gateway and platform. That means it is a form of middleware between computing clients and your API-based applications. Kong easily and consistently extends the features of your APIs. Some of the popular features deployed through Kong include authentication, security, traffic control, serverless, analytics & monitoring, request/response transformations and logging.
## Why Use KONG?
Compared to other API gateways and platforms, Kong has many important advantages that are not found in the market today. Choose Kong to ensure your API gateway platform is:

Radically Extensible
Blazingly Fast
Open Source
Platform Agnostic
Cloud Native
RESTful
These Kong advantages apply to both Kong and Kong Enterprise . The full set of Kong functionality is described in the publicly available documentation.

## How Does Kong Work?
A typical Kong setup is made of two main components:

Kong’s server, based on the widely adopted NGINX HTTP server, which is a reverse proxy processing your clients’ requests to your upstream services.
Kong’s datastore, in which the configuration is stored to allow you to horizontally scale Kong nodes. Apache Cassandra and PostgreSQL can be used to fulfill this role.
Kong needs to have both these components set up and operational.

 

### Kong server
The Kong Server, built on top of NGINX, is the server that will actually process the API requests and execute the configured plugins to provide additional functionalities to the underlying APIs before proxying the request upstream.

Kong listens on several ports that must allow external traffic and are by default:

8000 for proxying. This is where Kong listens for HTTP traffic. See proxy_listen.
8443 for proxying HTTPS traffic. See proxy_listen_ssl.
Additionally, those ports are used internally and should be firewalled in production usage:

8001 provides Kong’s Admin API that you can use to operate Kong. See admin_api_listen.
8444 provides Kong’s Admin API over HTTPS. See admin_api_ssl_listen.
You can use the Admin API to configure Kong, create new users, enable or disable plugins, and a handful of other operations. Since you will be using this RESTful API to operate Kong, it is also extremely easy to integrate Kong with existing systems.

### Kong datastore
Kong uses an external datastore to store its configuration such as registered APIs, Consumers and Plugins. Plugins themselves can store every bit of information they need to be persisted, for example rate-limiting data or Consumer credentials.

Kong maintains a cache of this data so that there is no need for a database roundtrip while proxying requests, which would critically impact performance. This cache is invalidated by the inter-node communication when calls to the Admin API are made. As such, it is discouraged to manipulate Kong’s datastore directly, since your nodes cache won’t be properly invalidated.

This architecture allows Kong to scale horizontally by simply adding new nodes that will connect to the same datastore and maintain their own cache.
## WHICH DATASTORES ARE SUPPORTED?
Apache Cassandra
PostgreSQL

## HOW DOES IT SCALE?
### Kong Server
Scaling the Kong Server up or down is fairly easy. Each server is stateless meaning you can add or remove as many nodes under the load balancer as you want as long as they point to the same datastore.

Be aware that terminating a node might interrupt any ongoing HTTP requests on that server, so you want to make sure that before terminating the node, all HTTP requests have been processed.

### Kong datastore
Scaling the datastore should not be your main concern, mostly because as mentioned before, Kong maintains its own cache, so expect your datastore’s traffic to be relatively quiet.

However, keep it mind that it is always a good practise to ensure your infrastructure does not contain single points of failure (SPOF). As such, closely monitor your datastore, and ensure replication of your data.

If you use Cassandra, one of its main advantages is its easy-to-use replication capabilities due to its distributed nature. Make sure to read the documentation pointed out by the Cassandra section of this FAQ.

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
                         
