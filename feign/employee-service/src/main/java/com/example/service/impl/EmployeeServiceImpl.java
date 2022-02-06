package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.DeparmentDto;
import com.example.model.Employee;
import com.example.repository.IEmployeeRepository;
import com.example.service.IDepartmentService;
import com.example.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeRepository repository;
	
	@Autowired
	private IDepartmentService departmentService;

	@Override
	public List<Employee> findAll() {
		return repository.findAll();
	}

	@Override
	public Employee save(Employee employee, String departamentName) {
		DeparmentDto deparmentDto = departmentService.findOneByName(departamentName);
		employee.setIdDepartment(deparmentDto.getId());
		return repository.save(employee);
	}

}
