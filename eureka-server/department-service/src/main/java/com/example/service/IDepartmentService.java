package com.example.service;

import java.util.List;

import com.example.model.Department;

public interface IDepartmentService {

	public List<Department> findAll();
	
	public Department findOneByName(String name);

}
