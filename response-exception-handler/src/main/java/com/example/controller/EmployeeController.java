package com.example.controller;

import javax.validation.constraints.NotBlank;

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

	@GetMapping("/{firstName}")
	public ResponseEntity<Employee> findByOneFirstName(@PathVariable("firstName") @NotBlank String firstName) {
		Employee employee = employeeService.findOneByFirstName(firstName);
		return ResponseEntity.ok(employee);
	}

	@PostMapping
	public ResponseEntity<Employee> create(@Validated @RequestBody Employee employee) {
		Employee employeeSave = employeeService.create(employee);
		return new ResponseEntity<Employee>(employeeSave, HttpStatus.CREATED);
	}

}
