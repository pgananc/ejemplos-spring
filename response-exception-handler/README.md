# response-exception-handler
Handler para excepción de respuesta cuando no cumple una validación del modelo o exepción en general.
# Requisitos
* Mínimo JDK 11
* Maven 3.8.2

# Ejecución.
* Ejecutar: mvn spring-boot:run

# EndPoints

* Post. Creación de empleado.
	
  	- http://localhost:8080/api/v1/employees
  	
  		- Ejemplo: {"firstName": "Marcos","lastName": "Cast" }
    
* Get. Búsqueda por id.
 
  	- http://localhost:8080/api/v1/employees/1

# Soporte.
pablos.ganan@gmail.com
