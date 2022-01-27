package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ContactDto;
import com.example.util.ConsumeMockApiUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/consume-rest-api-authorization")
public class ContactController {

	@Autowired
	ConsumeMockApiUtil consumeMockApiUtil;

	@PostMapping
	public ResponseEntity<Void> consumeRestApi(@RequestBody ContactDto contactDto) {
		try {
			consumeMockApiUtil.saveContact(contactDto);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

}
