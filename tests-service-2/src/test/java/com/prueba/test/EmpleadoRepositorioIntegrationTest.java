package com.prueba.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.prueba.modelo.Empleado;
import com.prueba.repositorio.IEmpleadoRepo;

@DataJpaTest
public class EmpleadoRepositorioIntegrationTest {

	@Autowired
	private IEmpleadoRepo empleadoRepo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void cuandoBuscaPorNombre_entoncesRetornoEmpleado() {
		Empleado empleado = new Empleado("pablo");
		entityManager.persistAndFlush(empleado);
		Empleado empleadoBuscado = empleadoRepo.findOneByNombre(empleado.getNombre());
		assertThat(empleadoBuscado.getNombre()).isEqualTo(empleado.getNombre());
	}

	@Test
	public void cuandoNombreEsInvalido_EntoncesRetornaNulo() {
		Empleado empleado = empleadoRepo.findOneByNombre("pepito");
		assertThat(empleado).isNull();
	}

	@Test
	public void cuandoBuscaPorId_entoncesRetornaEmpleado() {
		Empleado empleado = new Empleado("paty");
		entityManager.persistAndFlush(empleado);

		Empleado empleadoGuardado = empleadoRepo.findById(empleado.getId()).orElse(null);
		assertThat(empleadoGuardado.getNombre()).isEqualTo(empleado.getNombre());
	}

	@Test
	public void cuandoIdEsInvalido_entoncesRetornaNull() {
		Empleado empleadoBuscado = empleadoRepo.findById(-11l).orElse(null);
		assertThat(empleadoBuscado).isNull();
	}

	@Test
	public void dadoUnConjuntoEmpleados_entoncesRetornaTodosEmpleados() {
		Empleado alex = new Empleado("alex");
		Empleado ron = new Empleado("ron");
		Empleado bob = new Empleado("bob");

		entityManager.persist(alex);
		entityManager.persist(bob);
		entityManager.persist(ron);
		entityManager.flush();

		List<Empleado> allEmployees = empleadoRepo.findAll();

		assertThat(allEmployees).hasSize(3).extracting(Empleado::getNombre).containsOnly(alex.getNombre(),
				ron.getNombre(), bob.getNombre());
	}
}
