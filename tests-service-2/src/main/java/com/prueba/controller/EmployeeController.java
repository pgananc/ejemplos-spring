package com.prueba.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.exception.EmployeeNotFoundException;
import com.prueba.modelo.Employee;
import com.prueba.servicio.IEmployeeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	@Autowired
	IEmployeeService employeeService;

	@ApiOperation(value = "Retrieves a list of all Employee records")
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {

		List<Employee> employees = employeeService.getAllEmployees();
		if (employees.isEmpty()) {
			throw new EmployeeNotFoundException("No Employee records were found");
		}
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}

	@ApiOperation(value = "Retrives a single Employee record by its id")
	@GetMapping(value = "{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long id) {

		Optional<Employee> employeeToUpdate = employeeService.getEmployeeById(id);
		if (!employeeToUpdate.isPresent()) {
			throw new EmployeeNotFoundException("Employee with Id (" + id + ") not found!");
		}
		return new ResponseEntity<Employee>(employeeToUpdate.get(), HttpStatus.OK);
	}

	@ApiOperation(value = "Create a new Employee record. JSON payload will be validated")
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee createEmployee) {
		Employee employeeSave = employeeService.createEmployee(createEmployee);
		return new ResponseEntity<Employee>(employeeSave, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Update an existing Employee record. Will not create new record if Employee does not already exist")
	@PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee Employee) {
		Employee employeeUpdate = employeeService.updateEmployee(id, Employee);
		return new ResponseEntity<Employee>(employeeUpdate, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Delete an existing Employee record using its Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable(value = "id") Long id) {
		employeeService.deleteEmployee(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
