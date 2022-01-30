package com.example.pageable.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.pageable.model.Employee;

public interface IEmployeeService {

	public Page<Employee> listarPageable(Pageable pageable);

}
