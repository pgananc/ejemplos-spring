package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

	Employee  findOneByFirstName(String firstName);
}
