package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.DeparmentDto;
import com.example.service.IDepartmentClient;
import com.example.service.IDepartmentService;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

	@Autowired
	private IDepartmentClient departmentClient;

	@Override
	public DeparmentDto findOneByName(String name) {
		return departmentClient.findOneByName(name);
	}
	
	

}
