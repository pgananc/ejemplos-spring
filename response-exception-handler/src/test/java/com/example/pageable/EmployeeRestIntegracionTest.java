package com.example.pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.model.Employee;
import com.example.repository.IEmployeeRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeRestIntegracionTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private IEmployeeRepository employeeRepository;

	@AfterEach
	public void resetDb() {
		employeeRepository.deleteAll();
	}

	@Test
	public void dado_EmpleadoValido_entoncesCreaEmpleado() throws IOException, Exception {
		Employee employee = new Employee();
		employee.setFirstName("Carlos");
		employee.setLastName("Rambo");
		mvc.perform(
				post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(employee)));

		List<Employee> found = employeeRepository.findAll();
		assertThat(found).extracting(Employee::getFirstName).containsOnly("Carlos");
	}

	@Test
	public void dado_EmpleadoNoValido_entoncesRetornaCodigo400() throws IOException, Exception {
		Employee employee = new Employee();
		employee.setFirstName("Jhon");
		employee.setLastName("Rambo");
		mvc.perform(
				post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(employee)))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("firstName: First name must be between 5 and 50 characters ")));

	}

	@Test
	public void dadoListaEmpleado_entoncesEncuentraEmpleadoPorNombre() throws Exception {
		crearEmpleado("pablo", "perez");
		crearEmpleado("alexander", "castro");

		mvc.perform(get("/api/v1/employees/pablo").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.firstName", is("pablo")))
				.andExpect(jsonPath("$.lastName", is("perez")));
	}

	private void crearEmpleado(String fisrtName, String lastName) {
		Employee employee = new Employee();
		employee.setFirstName(fisrtName);
		employee.setLastName(lastName);
		employeeRepository.saveAndFlush(employee);
	}
}
