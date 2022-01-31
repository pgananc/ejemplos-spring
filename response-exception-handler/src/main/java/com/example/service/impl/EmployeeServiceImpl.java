package com.example.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.ModelNotFoundException;
import com.example.model.Employee;
import com.example.repository.IEmployeeRepository;
import com.example.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeRepository repository;

	@Override
	public Employee create(Employee employee) {
		return repository.save(employee);
	}

	@Override
	public Employee findOneByFirstName(String firstName) {
		Employee employee = repository.findOneByFirstName(firstName);
		if (Objects.isNull(employee)) {
			throw new ModelNotFoundException("Name not exist");
		} else {
			return employee;
		}
	}

}
