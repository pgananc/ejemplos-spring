package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Employee;
import com.example.service.IEmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;

	@GetMapping
	public ResponseEntity<List<Employee>> findAll() {
		List<Employee> listEmployee = employeeService.findAll();
		return ResponseEntity.ok(listEmployee);
	}

	@PostMapping("/{departamentName}")
	public ResponseEntity<Employee> save(@Validated @RequestBody Employee employee,
			@PathVariable(name = "departamentName") String departamentName) {
		employeeService.save(employee, departamentName);
		return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
	}

}
