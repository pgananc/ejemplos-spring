package com.example.pageable;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.model.Employee;
import com.example.repository.IEmployeeRepository;

@DataJpaTest
public class EmployeeRepositoryIntegrationTest {
	@Autowired
	private IEmployeeRepository employeeRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void dadoEmpleado_entoncesRetornoEmpleado() {
		Employee employee = new Employee();
		employee.setFirstName("Steven");
		employee.setLastName("Rambo");
		entityManager.persistAndFlush(employee);
		Employee employeeSearch = employeeRepository.findOneByFirstName(employee.getFirstName());
		assertThat(employeeSearch.getFirstName()).isEqualTo(employee.getFirstName());
	}

	@Test
	public void dadoNombreEmpleadoMinimo4Caracteres_entoncesRetornaErrorConstraintViolationException() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			Employee employee = new Employee();
			employee.setFirstName("Jhon");
			employee.setLastName("Rambo");
			entityManager.persistAndFlush(employee);
			Employee employeeSearch = employeeRepository.findOneByFirstName(employee.getFirstName());
			assertThat(employeeSearch.getFirstName()).isEqualTo(employee.getFirstName());
		});

	}
}
