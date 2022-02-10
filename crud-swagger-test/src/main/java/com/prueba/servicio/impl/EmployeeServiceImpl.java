package com.prueba.servicio.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.prueba.exception.EmployeeNotFoundException;
import com.prueba.modelo.Employee;
import com.prueba.repositorio.IEmployeeRepository;
import com.prueba.servicio.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	IEmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> getEmployeeById(Long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public Employee createEmployee(Employee newEmployee) {
		if (Objects.nonNull(newEmployee.getId())) {
			validateExistEmployee(newEmployee);
		}
		validateAgeEmployee(newEmployee.getBirthDate());
		return employeeRepository.save(newEmployee);

	}

	private void validateExistEmployee(Employee newEmployee) {
		Optional<Employee> employee = employeeRepository.findById(newEmployee.getId());
		if (employee.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.CONFLICT,
					"Employee with Id (" + newEmployee.getId() + ")  exists!");
		}
	}

	@Override
	public Employee updateEmployee(Long id, Employee employeeUpdate) {

		if (!id.equals(employeeUpdate.getId())) {
			throw new HttpClientErrorException(HttpStatus.CONFLICT, "Id in URI does not match Employee id to update");
		}

		Optional<Employee> employee = validateNoExistEmployee(id);
		Employee orginalEmployee = employee.get();
		BeanUtils.copyProperties(employeeUpdate, orginalEmployee);
		return employeeRepository.save(orginalEmployee);
	}

	@Override
	public void deleteEmployee(Long id) {
		Optional<Employee> employee = validateNoExistEmployee(id);
		employeeRepository.delete(employee.get());
	}

	private Optional<Employee> validateNoExistEmployee(Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);

		if (!employee.isPresent()) {
			throw new EmployeeNotFoundException("Employee with Id (" + id + ") not found!");
		}
		return employee;
	}

	private void validateAgeEmployee(LocalDate birthDate) {
		int age = Period.between(birthDate, LocalDate.now()).getYears();
		if (age < 18) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					"You can't register employees underage.Review birth date.");
		}

	}

}
