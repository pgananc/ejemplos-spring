# crud-sample
Ejemplo de CRUD básico.
# Requisitos
* Mínimo JDK 11
* Maven 3.8.2
# Ejecución.
* Ejecutar: mvn clean install
# EndPoints
* Create (Post)
  * http://localhost:8080/api/v1/employees
    * Example:
     {
	"firstName":"John",
	"lastName":"Rambo"
    }
* Read (Get)
  - http://localhost:8080/api/v1/employees
* Update (Put)
  - http://localhost:8080/api/v1/employees
    * Example:
      { "id":1,
	"firstName":"John",
	"lastName":"Susnavas"
    }
* Delete (Delete)
  - http://localhost:8080/api/v1/employees
     * Example: http://localhost:8080/api/v1/employees/1
# Soporte.
pablos.ganan@gmail.com
