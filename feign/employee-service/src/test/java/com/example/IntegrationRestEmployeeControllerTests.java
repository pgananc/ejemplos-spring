package com.example;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.model.Employee;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationRestEmployeeControllerTests {

	@Autowired
	private MockMvc mvc;



	private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));



	@Test
	public void dadoAEmpleadoYNombreDepartamento_entoncesRegistraEmpleado1() throws Exception {
		String nombreDepartamento = "Ventas";

		Employee employee = Employee.builder().firstName("Juanito").lastName("Perez").build();
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.post("/api/v1/employees/".concat(nombreDepartamento)).contentType(contentType)
				.content(JsonUtil.toJson(employee));
		mvc.perform(mockRequest).andDo(print()).andExpect(status().isCreated());
	}

	@Test
	public void dadoBConsultaEmpleados_entoncesRertornaTodosEmpleados() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
	}

}
