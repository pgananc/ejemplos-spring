package com.example.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dto.ContactDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsumeMockApiUtil {
	/**
	 * Property in application.properties.
	 */
	@Value("${url.mocklab.api}")
	private String urlMockLab;

	@Value("${url.mocklab.password}")
	private String password;

	@Autowired
	private RestTemplate restTemplate;

	public int saveContact(ContactDto contactDto) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		String requestJson = objectMapper.writeValueAsString(contactDto);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic ".concat(password));
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

		restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.postForEntity(urlMockLab, entity, Object.class);
		return response.getStatusCodeValue();

	}
}
