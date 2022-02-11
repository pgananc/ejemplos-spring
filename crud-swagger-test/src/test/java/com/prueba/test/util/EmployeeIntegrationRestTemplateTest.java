package com.prueba.test.util;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.prueba.TestsServiceApplication;
import com.prueba.modelo.Employee;
import com.prueba.repositorio.IEmployeeRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = TestsServiceApplication.class)
@ActiveProfiles("integration")
@TestMethodOrder(OrderAnnotation.class)
public class EmployeeIntegrationRestTemplateTest {
	final private static int port = 8080;
	final private static String baseUrl = "http://localhost:";
	final private static String path = "/api/v1/employees";
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private IEmployeeRepository employeeRepository;

	@Test
	@Order(1)
	public void post_createNewEmployee_Returns_201_Created() {

		Employee newEmployee = Employee.builder().id(1L).firstName("Pablo").lastName("Perez")
				.birthDate(LocalDate.of(1984, 12, 10)).build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Employee> request = new HttpEntity<Employee>(newEmployee, headers);

		ResponseEntity<Employee> responseEntity = this.restTemplate.postForEntity(baseUrl + port + path + "/create",
				request, Employee.class);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(1, responseEntity.getBody().getId());
		assertEquals("Pablo", responseEntity.getBody().getFirstName());
		assertEquals("Perez", responseEntity.getBody().getLastName());
		assertEquals(LocalDate.of(1984, 12, 10), responseEntity.getBody().getBirthDate());

		Optional<Employee> op = employeeRepository.findById(1L);
		assertTrue(op.isPresent());
		assertEquals(1L, op.get().getId());
	}

	@Test
	@Order(2)
	public void get_employeeById_Returns_NotFound_404() {

		ResponseEntity<String> result = this.restTemplate.exchange(baseUrl + port + path + "/2", HttpMethod.GET, null,
				String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonTree = null;
		try {
			jsonTree = mapper.readTree(result.getBody());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		JsonNode jsonNode = jsonTree.get("errorMessage");

		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertTrue(jsonNode.asText().contains("404 Employee with Id (2) not found!"));
	}

	@Test
	@Order(3)
	public void post_createNewEmployee_Returns_400_BadRequest() {

		ResponseEntity<String> result = null;

		Employee newEmployee = Employee.builder().id(1L).firstName("Pablo").lastName("")
				.birthDate(LocalDate.of(1984, 12, 10)).build();

		ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
	            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		JsonNode jsonNode = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		try {
			String vehicleJSONString = mapper.writeValueAsString(newEmployee);

			HttpEntity<String> request = new HttpEntity<String>(vehicleJSONString, headers);
			result = this.restTemplate.postForEntity(baseUrl + port + path + "/create", request, String.class);
			jsonNode = mapper.readTree(result.getBody()).get("errorMessage");

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertTrue(jsonNode.asText().contains("'lastName' field was empty"));
	}

	@Test
	@Order(4)
	public void put_updateEmployee_Returns_202_Accepted() {

		Employee vehicleUpdate = Employee.builder().id(1L).firstName("Pablo").lastName("Perez")
				.birthDate(LocalDate.of(1984, 12, 10)).build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Employee> requestEntity = new HttpEntity<Employee>(vehicleUpdate, headers);

		ResponseEntity<Employee> responseEntity = this.restTemplate.exchange(baseUrl + port + path + "/update/1",
				HttpMethod.PUT, requestEntity, Employee.class);

		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
		assertEquals(vehicleUpdate, responseEntity.getBody());
	}

	@Test
	@Order(5)
	public void get_vehicleById_Returns_Employee_OK() {

		ResponseEntity<Employee> responseEntity = this.restTemplate.getForEntity(baseUrl + port + path + "/1",
				Employee.class);

		Employee expectedEmployee = Employee.builder().id(1L).firstName("Pablo").lastName("Perez")
				.birthDate(LocalDate.of(1984, 12, 10)).build();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedEmployee, responseEntity.getBody());
	}

	@Test
	@Order(6)
	public void get_allEmployees_ReturnsAllEmployees_OK() {

		List<Long> expectedIdList = Stream.of(1L).collect(Collectors.toList());

		ResponseEntity<List<Employee>> responseEntity = this.restTemplate.exchange(baseUrl + port + path,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
				});

		List<Employee> employeesResponseList = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(employeesResponseList.size() > 0);
		assertTrue(employeesResponseList.stream().anyMatch((employee) -> {
			return expectedIdList.contains(employee.getId());
		}));
	}

	@Test
	@Order(7)
	public void delete_employeeById_Returns_NoContent_204() {

		ResponseEntity<Object> responseEntity = this.restTemplate.exchange(baseUrl + port + path + "/1",
				HttpMethod.DELETE, null, Object.class);

		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());

		Optional<Employee> optional = employeeRepository.findById(1L);
		assertFalse(optional.isPresent());
	}
}
