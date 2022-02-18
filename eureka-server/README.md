# eureka-server

Utilización de eureka-server para descubrimiento de servicios.
# Requisitos
* Mínimo JDK 11
* Maven 3.8.2

# Ejecución
* Ejecutar:mvn spring-boot:run
	* <a href="https://github.com/pgananc/ejemplos-spring/tree/main/eureka-server/department-service">department-service</a>. 
	* <a href="https://github.com/pgananc/ejemplos-spring/tree/main/eureka-server/employee-service-client">employee-service-client</a>. 
	* <a href="https://github.com/pgananc/ejemplos-spring/tree/main/eureka-server/eureka-server">eureka-server</a>. 
	

# EndPoints

* Post. 
```
	 http://localhost:8080/api/v1/employees/Ventas
	 
	{
	"firstName":"John",
	"lastName":"Rambo"
	}
```
# Referencias

* <a href="https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html">Feign</a>.
* <a href="https://cloud.spring.io/spring-cloud-netflix/multi/multi__service_discovery_eureka_clients.html">Eureka clients</a>.

# Soporte
pablos.ganan@gmail.com
