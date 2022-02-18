# eureka-server

Utilización de eureka-server para descubrimiento de servicios.
# Requisitos
* Mínimo JDK 11
* Maven 3.8.2

# Ejecución
* Ejecutar:mvn spring-boot:run
	* department-service
	* employee-service-client
	* eureka-server

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
