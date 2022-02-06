package com.example.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.config.FeignConfig;
import com.example.dto.DeparmentDto;
import com.example.hystrix.DepartmentClientFallback;

@FeignClient(name = "localhost", url = "localhost:8080/api/v1/deparments", configuration = FeignConfig.class, fallback = DepartmentClientFallback.class)
public interface IDepartmentClient {

	@GetMapping("/{name}")
	DeparmentDto findOneByName(@PathVariable("name") String name);

}
