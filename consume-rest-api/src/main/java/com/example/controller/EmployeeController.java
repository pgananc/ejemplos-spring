package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.EmployeeDto;
import com.example.util.ConsumeMockApiUtil;

@RestController
@RequestMapping("/api/v1/consume-rest-api")
public class EmployeeController {

	@Autowired
	ConsumeMockApiUtil consumeMockApiUtil;

	@GetMapping
	public ResponseEntity<List<EmployeeDto>> consumeRestApi() {
		return new ResponseEntity<List<EmployeeDto>>(consumeMockApiUtil.consumeMockApi(), HttpStatus.OK);
	}

}
