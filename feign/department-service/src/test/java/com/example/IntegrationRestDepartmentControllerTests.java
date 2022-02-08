package com.example;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationRestDepartmentControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void cuandoConsultaDepartementos_entoncesRetornaTodosDepartametos() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/deparments").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Ventas"));
	}

	@Test
	public void dadoNombreDepartamento_entoncesDevuleveDepartamento() throws Exception {
		String nameDepartament = "Ventas";
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/deparments/".concat(nameDepartament))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty());
	}

}
