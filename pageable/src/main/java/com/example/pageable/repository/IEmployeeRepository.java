package com.example.pageable.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pageable.model.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

}
