package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import com.example.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Override
	public Employee create(Employee employee) {
		return repository.save(employee);
	}

	@Override
	public List<Employee> read() {
		return repository.findAll();
	}

	@Override
	public Employee update(Employee employee) {
		return repository.save(employee);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
