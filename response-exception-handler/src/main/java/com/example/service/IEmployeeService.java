package com.example.service;

import com.example.model.Employee;

public interface IEmployeeService {

	Employee create(Employee employee);

	Employee findOneByFirstName(String firstName);

}
