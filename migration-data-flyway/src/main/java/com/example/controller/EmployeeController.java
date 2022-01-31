package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Employee;
import com.example.service.IEmployeeService;

@RestController
@RequestMapping("/api/v1/employees-migration")
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;

	@GetMapping
	public ResponseEntity<List<Employee>> findAll() {
		List<Employee> employees = employeeService.findAll();
		return ResponseEntity.ok(employees);
	}

}
