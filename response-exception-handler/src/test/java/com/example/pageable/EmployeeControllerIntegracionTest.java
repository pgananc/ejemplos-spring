package com.example.pageable;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.model.Employee;
import com.example.service.IEmployeeService;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerIntegracionTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IEmployeeService employeeService;

	@Test
	public void cuandoEmpleadoEsValido_entoncesCrearEmpleado() throws Exception {
		Employee employee = new Employee();
		employee.setFirstName("Steven");
		employee.setLastName("Rambo");
		given(employeeService.create(Mockito.any())).willReturn(employee);

		mvc.perform(
				post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(employee)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", is("Steven")));
		verify(employeeService, VerificationModeFactory.times(1)).create(Mockito.any());
		reset(employeeService);
	}

	@Test
	public void cuandoNombreDelEmpleadoNoEsValido_entoncesNoCrearEmpleado() throws Exception {
		Employee employee = new Employee();
		employee.setFirstName("Jhon");
		employee.setLastName("Rambo");
		given(employeeService.create(Mockito.any())).willReturn(employee);

		mvc.perform(
				post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(employee)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("firstName: First name must be between 5 and 50 characters ")));
	}

	@Test
	public void dadoNombreEmpleado_entoncesRetornaEmpleadoEncontrado() throws Exception {
		Employee employee = new Employee();
		employee.setFirstName("Steven");
		employee.setLastName("Rambo");
		given(employeeService.findOneByFirstName(Mockito.any())).willReturn(employee);

		mvc.perform(get("/api/v1/employees/Steven").contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(employee))).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Steven")));
		verify(employeeService, VerificationModeFactory.times(1)).findOneByFirstName(Mockito.any());
		reset(employeeService);
	}
}
