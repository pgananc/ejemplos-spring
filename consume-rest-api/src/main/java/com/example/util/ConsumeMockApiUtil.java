package com.example.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dto.EmployeeDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsumeMockApiUtil {
	/**
	 * Property in application.properties.
	 */
	@Value("${url.mock.api}")
	private String urlMockApi;

	@Autowired
	private RestTemplate restTemplate;

	public List<EmployeeDto> consumeMockApi() {
		ObjectMapper mapper = new ObjectMapper();
		ResponseEntity<EmployeeDto[]> response = restTemplate.getForEntity(urlMockApi, EmployeeDto[].class);
		return Arrays.stream(response.getBody()).map(employee -> mapper.convertValue(employee, EmployeeDto.class))
				.collect(Collectors.toList());
	}

}
