package com.example.pageable;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.model.Employee;
import com.example.repository.IEmployeeRepository;
import com.example.service.IEmployeeService;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeServiceImplIntegracionTest {

	@Autowired
	private IEmployeeService employeeService;

	@MockBean
	private IEmployeeRepository employeeRepository;

	@BeforeEach
	public void setUp() {
		Employee employee = new Employee();
		employee.setFirstName("Steven");
		employee.setLastName("Rambo");
		employee.setId(11L);
		Mockito.when(employeeRepository.findOneByFirstName(employee.getFirstName())).thenReturn(employee);
		Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
	}

	@Test
	public void cuandoNombreEsValido_entoncesEmpleadoDeberiaEncontrarse() {
		String nombre = "Steven";
		Employee employeeSearch = employeeService.findOneByFirstName(nombre);
		assertThat(employeeSearch.getFirstName()).isEqualTo(nombre);
	}

	@Test
	public void dadoDatosEmpleado_entoncesEmpleadoDeberiaGuardarse() {
		Employee employee = new Employee();
		employee.setFirstName("Steven");
		employee.setLastName("Rambo");
		employee.setId(11L);
		Employee employeeSave = employeeService.create(employee);
		assertThat(employeeSave.getId()).isEqualTo(employee.getId());
	}
}
