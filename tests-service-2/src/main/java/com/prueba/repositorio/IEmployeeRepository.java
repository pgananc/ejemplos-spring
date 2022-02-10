package com.prueba.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.modelo.Employee;

public interface IEmployeeRepository  extends JpaRepository<Employee, Long>{

	
}
