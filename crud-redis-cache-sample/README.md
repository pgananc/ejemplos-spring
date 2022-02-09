# crud-redis-cache-sample

Ejemplo de CRUD redis.
# Requisitos

* Mínimo JDK 11
* Maven 3.8.2
* Redis


# Ejecución.

```
	docker run -p 6379:6379 --name some-redis -d redis
```

* Ejecutar: mvn clean install

### EndPoints
* Create (Post)

```
  http://localhost:9292/api/v1/employees

    Example:
    {
	"id":2,
	"firstName":"John",
	"lastName":"Rambo",
	"state":true
	}
```
* Read (Get)

```
  http://localhost:9292/api/v1/employees/2
```

* Delete (Delete)

```
  http://localhost:9292/api/v1/employees/2
```
# Extra
Configuración de <a href="https://hub.docker.com/r/rediscommander/redis-commander">redis commnader</a>

Console web: http://127.0.0.1:8081

    
# Soporte.
pablos.ganan@gmail.com