package com.example.redis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis.entity.Employee;
import com.example.redis.service.IEmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	@Autowired
	private IEmployeeService dao;

	@PostMapping
	public ResponseEntity<Employee> save(@RequestBody Employee employee) {
		Employee employeeSave = dao.save(employee);
		return new ResponseEntity<Employee>(employeeSave, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Object>> getAllEmployees() {
		List<Object> lista = dao.findAll();
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	@Cacheable(key = "#id", value = "Employee", unless = "#result.state==false")
	public Employee findEmployee(@PathVariable Long id) {
		Employee employee = dao.findEmployeeById(id);
		return employee;
	}

	@DeleteMapping("/{id}")
	@CacheEvict(key = "#id", value = "Employee")
	public ResponseEntity<Void> remove(@PathVariable Long id) {
		dao.deleteEmployee(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
