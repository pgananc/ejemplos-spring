package com.example.pageable;

import static com.example.pageable.util.PageableAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import com.example.pageable.repository.IEmployeeRepository;

@SpringBootTest
@AutoConfigureMockMvc
class PageableApplicationTests {

	@MockBean
	private IEmployeeRepository employeeRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void dado_parametros_entonces_retorna_ordenamiento() throws Exception {

		mockMvc.perform(get("/api/v1/employees/pageable").param("page", "5").param("size", "10")
				.param("sort", "id,desc").param("sort", "firstName,asc")).andExpect(status().isOk());

		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		verify(employeeRepository).findAll(pageableCaptor.capture());
		PageRequest pageable = (PageRequest) pageableCaptor.getValue();

		assertThat(pageable).hasPageNumber(5);
		assertThat(pageable).hasPageSize(10);
		assertThat(pageable).hasSort("firstName", Sort.Direction.ASC);
		assertThat(pageable).hasSort("id", Sort.Direction.DESC);
	}

	@Test
	void dado_consulta_entonces_devuleve_parametros_default() throws Exception {

		mockMvc.perform(get("/api/v1/employees/pageable")).andExpect(status().isOk());

		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		verify(employeeRepository).findAll(pageableCaptor.capture());
		PageRequest pageable = (PageRequest) pageableCaptor.getValue();

		assertThat(pageable).hasPageNumber(0);
		assertThat(pageable).hasPageSize(20);
	}

}
