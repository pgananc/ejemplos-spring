package com.example.pageable;

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
class MigrationDataFlywayApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void dada_migracion_entonces_ids_no_deben_estar_nulos() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees-migration").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("pepito"));
	}

	@Test
	public void dada_migracion_entonces_nombre_primer_registro_es_pepito() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees-migration").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("pepito"));
	}

}
