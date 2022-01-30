# pageable
Paginación de consulta.
# Requisitos
* Mínimo JDK 11
* Maven 3.8.2
# Ejecución.
* Ejecutar: mvn spring-boot:run
# EndPoints
* Get. Ordenamiento por lastName, páginas 0, tamaño 3.
  - http://localhost:8080/api/v1/employees/pageable?page=0&size=3&sort=lastName
* Get. Ordenamiento por firsName desc, páginas 2, tamaño 5.
  - http://localhost:8080/api/v1/employees/pageable?page=2&size=5&sort=firstName,desc
* Get. Consulta todos los registros.
  - http://localhost:8080/api/v1/employees/pageable
# Soporte.
pablos.ganan@gmail.com
