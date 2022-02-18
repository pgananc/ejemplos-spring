package com.example.service;

import java.util.List;

import com.example.model.Employee;

public interface IEmployeeService {

	List<Employee> findAll();

	Employee save(Employee employee, String departamentName);

}
