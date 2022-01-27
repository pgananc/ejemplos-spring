package com.prueba.test;

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

import com.prueba.modelo.Empleado;
import com.prueba.repositorio.IEmpleadoRepo;
import com.prueba.test.util.JsonUtil;

@SpringBootTest
@AutoConfigureMockMvc
class EmpleadoRestControllerIntegrationTest {

	 @Autowired
	    private MockMvc mvc;

	    @Autowired
	    private IEmpleadoRepo empleadoRepo;

	    @AfterEach
	    public void resetDb() {
	        empleadoRepo.deleteAll();
	    }

	    @Test
	    public void whenValidInput_thenCreateEmpleado() throws IOException, Exception {
	        Empleado empleado = new Empleado("pablo");
	        mvc.perform(post("/api/empleados").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(empleado)));

	        List<Empleado> found = empleadoRepo.findAll();
	        assertThat(found).extracting(Empleado::getNombre).containsOnly("pablo");
	    }

	    @Test
	    public void givenEmpleados_whenGetEmpleados_thenStatus200() throws Exception {
	        crearEmpleado("pablo");
	        crearEmpleado("alex");

	        mvc.perform(get("/api/empleados").contentType(MediaType.APPLICATION_JSON))
	          .andDo(print())
	          .andExpect(status().isOk())
	          .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
	          .andExpect(jsonPath("$[0].nombre", is("pablo")))
	          .andExpect(jsonPath("$[1].nombre", is("alex")));
	    }

	    private void crearEmpleado(String nombre) {
	        Empleado emp = new Empleado(nombre);
	        empleadoRepo.saveAndFlush(emp);
	    }



}
