package com.example.service;

import com.example.dto.DeparmentDto;

public interface IDepartmentService {

	DeparmentDto findOneByName(String name);

}
