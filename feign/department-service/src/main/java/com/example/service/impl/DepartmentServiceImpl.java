package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Department;
import com.example.repository.IDepartmentRepository;
import com.example.service.IDepartmentService;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

	@Autowired
	private IDepartmentRepository repository;

	@Override
	public List<Department> findAll() {
		return repository.findAll();
	}

	@Override
	public Department findOneByName(String name) {
		return repository.findOneByName(name);
	}

}
