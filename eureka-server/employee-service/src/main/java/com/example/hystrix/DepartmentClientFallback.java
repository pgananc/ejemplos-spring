package com.example.hystrix;

import org.springframework.stereotype.Component;

import com.example.dto.DeparmentDto;
import com.example.service.IDepartmentClient;

@Component
public class DepartmentClientFallback implements IDepartmentClient {

	@Override
	public DeparmentDto findOneByName(String name) {
		return DeparmentDto.builder().id(0l).name("").build();
	}

}
