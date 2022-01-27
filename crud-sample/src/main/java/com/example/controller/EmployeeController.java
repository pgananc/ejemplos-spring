package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Employee;
import com.example.service.EmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	@PostMapping
	public ResponseEntity<Employee> create(@Validated @RequestBody Employee employee) {
		Employee employeeSave = service.create(employee);
		return new ResponseEntity<Employee>(employeeSave, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Employee>> read() {
		List<Employee> list = service.read();
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Employee> update(@Validated @RequestBody Employee employee) {
		Employee employeeSave = service.update(employee);
		return new ResponseEntity<Employee>(employeeSave, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
