package com.prueba.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.prueba.modelo.Empleado;
import com.prueba.servicio.IEmpleadoServicio;
import com.prueba.test.util.JsonUtil;


@SpringBootTest
@AutoConfigureMockMvc
class EmpleadoControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IEmpleadoServicio empleadoServicio;

	
	@Test
	public void cuandoEmpleadoPost_entoncesCrearEmpleado() throws Exception {
		Empleado empleado = new Empleado("Pablo");
		given(empleadoServicio.guardar(Mockito.any())).willReturn(empleado);

		mvc.perform(post("/api/empleados").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(empleado)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.nombre", is("Pablo")));
		verify(empleadoServicio, VerificationModeFactory.times(1)).guardar(Mockito.any());
		reset(empleadoServicio);
	}

	@Test
	public void dadoEmpleados_cuandoObtengoEmpleados_entoncesRetornaJsonArray() throws Exception {
		Empleado pablo = new Empleado("pablo");
		Empleado john = new Empleado("john");
		Empleado bob = new Empleado("bob");

		List<Empleado> allEmployees = Arrays.asList(pablo, john, bob);

		given(empleadoServicio.obtenerTodos()).willReturn(allEmployees);

		mvc.perform(get("/api/empleados").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[0].nombre", is(pablo.getNombre())))
				.andExpect(jsonPath("$[1].nombre", is(john.getNombre())))
				.andExpect(jsonPath("$[2].nombre", is(bob.getNombre())));
		verify(empleadoServicio, VerificationModeFactory.times(1)).obtenerTodos();
		reset(empleadoServicio);
	}

}
