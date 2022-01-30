package com.example.pageable.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.pageable.model.Employee;
import com.example.pageable.repository.IEmployeeRepository;
import com.example.pageable.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeRepository repository;

	@Override
	public Page<Employee> listarPageable(final Pageable pageable) {
		return repository.findAll(pageable);
	}

}
