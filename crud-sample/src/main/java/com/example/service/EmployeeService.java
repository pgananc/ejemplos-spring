package com.example.service;

import java.util.List;

import com.example.model.Employee;

public interface EmployeeService {

	Employee create(Employee employee);

	List<Employee> read();

	Employee update(Employee employee);

	void delete(Long id);

}
