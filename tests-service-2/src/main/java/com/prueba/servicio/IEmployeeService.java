package com.prueba.servicio;

import java.util.List;
import java.util.Optional;

import com.prueba.modelo.Employee;

public interface IEmployeeService {

	Employee createEmployee(Employee newEmployee);

	Employee updateEmployee(Long id, Employee employeeUpdate);

	List<Employee> getAllEmployees();

	Optional<Employee> getEmployeeById(Long id);

	void deleteEmployee(Long id);
}
