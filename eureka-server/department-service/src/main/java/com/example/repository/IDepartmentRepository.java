package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Department;

public interface IDepartmentRepository extends JpaRepository<Department, Long> {

	public Department findOneByName(String name);
}
