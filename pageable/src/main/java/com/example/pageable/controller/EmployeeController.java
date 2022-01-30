package com.example.pageable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pageable.model.Employee;
import com.example.pageable.service.IEmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;

	@GetMapping("pageable")
	public ResponseEntity<Page<Employee>> findPageable(Pageable pageable) {
		Page<Employee> employees = employeeService.listarPageable(pageable);
		return ResponseEntity.ok(employees);
	}

}
