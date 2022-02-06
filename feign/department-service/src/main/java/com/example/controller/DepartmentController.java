package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Department;
import com.example.service.IDepartmentService;

@RestController
@RequestMapping("/api/v1/deparments")
public class DepartmentController {

	@Autowired
	private IDepartmentService departmentService;

	@GetMapping
	public ResponseEntity<List<Department>> findAll() {
		List<Department> employees = departmentService.findAll();
		return ResponseEntity.ok(employees);
	}
	@GetMapping("/{name}")
	public ResponseEntity<Department> findOneByName(@PathVariable(name = "name") String name) {
		Department department= departmentService.findOneByName(name);
		return ResponseEntity.ok(department);
	}

}
