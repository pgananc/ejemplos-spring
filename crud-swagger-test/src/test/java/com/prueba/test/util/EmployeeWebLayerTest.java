package com.prueba.test.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.controller.EmployeeController;
import com.prueba.exception.EmployeeNotFoundException;
import com.prueba.modelo.Employee;
import com.prueba.servicio.IEmployeeService;

@WebMvcTest(EmployeeController.class)
@ActiveProfiles("test")
public class EmployeeWebLayerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	@MockBean
	IEmployeeService employeeService;

	@Test
	public void get_allEmployees_returnsOkWithListOfEmployees() throws Exception {

		List<Employee> employeeList = new ArrayList<>();
		Employee employee1 = Employee.builder().id(1L).firstName("Pablo").lastName("Perez")
				.birthDate(LocalDate.of(2020, 01, 10)).build();
		Employee employee2 = Employee.builder().id(2L).firstName("Ana").lastName("Perez")
				.birthDate(LocalDate.of(2020, 01, 10)).build();
		employeeList.add(employee1);
		employeeList.add(employee2);

		Mockito.when(employeeService.getAllEmployees()).thenReturn(employeeList);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].firstName", is("Pablo"))).andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].firstName", is("Ana")));
	}

	@Test
	public void post_createsNewEmployee_andReturnsObjWith201() throws Exception {
		Employee employee = Employee.builder().id(1L).firstName("Pablo").lastName("Perez")
				.birthDate(LocalDate.of(2020, 01, 10)).build();
		Mockito.when(employeeService.createEmployee(Mockito.any(Employee.class))).thenReturn(employee);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/employees/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(employee));

		mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(1)))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(employee)));
	}

	@Test
	public void post_submitsInvalidEmployee_WithEmptyLastName_Returns400() throws Exception {
		// Create new vehicle with empty 'make' field
		Employee employee = Employee.builder().firstName("Pablo").lastName("").birthDate(LocalDate.of(2020, 01, 10))
				.build();

		String vehicleJsonString = this.mapper.writeValueAsString(employee);

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees/create")
				.contentType(MediaType.APPLICATION_JSON).content(vehicleJsonString)).andExpect(status().isBadRequest());

		assertEquals(MethodArgumentNotValidException.class,
				resultActions.andReturn().getResolvedException().getClass());
		assertTrue(
				resultActions.andReturn().getResolvedException().getMessage().contains("'lastName' field was empty"));
	}

	@Test
	public void put_updatesAndReturnsUpdatedObjWith202() throws Exception {
		Employee employee = Employee.builder().id(1L).firstName("Pablo").lastName("Perez")
				.birthDate(LocalDate.of(2020, 01, 10)).build();

		Mockito.when(employeeService.updateEmployee(1L, employee)).thenReturn(employee);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/v1/employees/update/1", employee)
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(employee));

		mockMvc.perform(builder).andExpect(status().isAccepted()).andExpect(jsonPath("$.id", is(1)))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(employee)));
	}

	@Test
	public void delete_deleteEmployee_Returns204Status() throws Exception {
		Long employeeId = 1L;

		IEmployeeService serviceSpy = Mockito.spy(employeeService);
		Mockito.doNothing().when(serviceSpy).deleteEmployee(employeeId);

		mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/v1/employees/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(employeeService, times(1)).deleteEmployee(employeeId);
	}

	@Test
	public void get_vehicleById_ThrowsEmployeeNotFoundException() throws Exception {

		Mockito.when(employeeService.getEmployeeById(1L)).thenReturn(Optional.empty());

		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.get("/api/v1/employees/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		assertEquals(EmployeeNotFoundException.class, resultActions.andReturn().getResolvedException().getClass());
		assertTrue(resultActions.andReturn().getResolvedException().getMessage()
				.contains("Employee with Id (1) not found!"));
	}
}
