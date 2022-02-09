package com.example.redis.service;

import java.util.List;

import com.example.redis.entity.Employee;

public interface IEmployeeService {

	String HASH_KEY = "Employee";

	public Employee save(Employee employee);

	public List<Object> findAll();

	public Employee findEmployeeById(Long id);

	public void deleteEmployee(Long id);
}
