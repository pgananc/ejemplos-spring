package com.example.redis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.redis.entity.Employee;
import com.example.redis.service.IEmployeeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();

	public Employee save(Employee employee) {
		template.opsForHash().put(HASH_KEY, employee.getId(), employee);
		return employee;
	}

	public List<Object> findAll() {
		return template.opsForHash().values(HASH_KEY);
	}

	public Employee findEmployeeById(Long id) {
		log.info("called findEmployeeById() from DB");
		return (Employee) template.opsForHash().get(HASH_KEY, id);
	}

	public void deleteEmployee(Long id) {
		template.opsForHash().delete(HASH_KEY, id);
	}
}
