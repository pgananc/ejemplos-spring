package com.prueba.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.prueba.modelo.Empleado;
import com.prueba.repositorio.IEmpleadoRepo;
import com.prueba.servicio.IEmpleadoServicio;

@SpringBootTest
@AutoConfigureMockMvc
class EmpleadoServicioImplIntegracionTest {

	@Autowired
	private IEmpleadoServicio empleadoServicio;

	@MockBean
	private IEmpleadoRepo empleadoRepo;

	@BeforeEach
	public void setUp() {
		Empleado pablo = new Empleado("pablo");
		pablo.setId(11L);

		Empleado juan = new Empleado("juan");
		Empleado javier = new Empleado("javier");

		List<Empleado> empleados = Arrays.asList(pablo, juan, javier);

		Mockito.when(empleadoRepo.findOneByNombre(pablo.getNombre())).thenReturn(pablo);
		Mockito.when(empleadoRepo.findOneByNombre(juan.getNombre())).thenReturn(juan);
		Mockito.when(empleadoRepo.findOneByNombre("otroNombre")).thenReturn(null);
		Mockito.when(empleadoRepo.findById(pablo.getId())).thenReturn(Optional.of(pablo));
		Mockito.when(empleadoRepo.findAll()).thenReturn(empleados);
		Mockito.when(empleadoRepo.findById(-99L)).thenReturn(Optional.empty());
	}

	@Test
	public void cuandoNombreEsValido_entoncesEmpleadoDeberiaEncontrarse() {
		String nombre = "pablo";
		Empleado empleadoEncontrado = empleadoServicio.buscarPorNombre(nombre);

		assertThat(empleadoEncontrado.getNombre()).isEqualTo(nombre);
	}

	@Test
	public void cuandoNombreEsInvalido_entoncesEmpleadoNoEsEncontrado() {
		Empleado fromDb = empleadoServicio.buscarPorNombre("otroNombre");
		assertThat(fromDb).isNull();

		verificarBusquePorNombreLamadaUnaVez("otroNombre");
	}

	@Test
	public void cuandoNombreExiste_entoncesDebeExistirEmpleado() {
		boolean empleadoExiste = empleadoServicio.validarExisteEmpleado("juan");
		assertThat(empleadoExiste).isEqualTo(true);

		verificarBusquePorNombreLamadaUnaVez("juan");
	}

	@Test
	public void cuandoNombreNoExiste_entoncesNoDebeExistir() {
		boolean empleadoExiste = empleadoServicio.validarExisteEmpleado("pepito");
		assertThat(empleadoExiste).isEqualTo(false);

		verificarBusquePorNombreLamadaUnaVez("pepito");
	}

	@Test
	public void cuandoIdEsValido_entonceElEmpleadoDebeSerEncontrado() {
		Empleado empleadoBDD = empleadoServicio.buscarPorId(11L);
		assertThat(empleadoBDD.getNombre()).isEqualTo("pablo");
		verificarBusquePorId();
	}

	@Test
	public void cuandoIdEsInvalido_entoncesEmpleadoNoDebeSerEncontrado() {
		Empleado fromDb = empleadoServicio.buscarPorId(-99L);
		verificarBusquePorId();
		assertThat(fromDb).isNull();
	}

	@Test
	public void dado3Empleados_cuandoObtineTodos_entoncesRetornar3Registros() {
		Empleado pablo = new Empleado("pablo");
		Empleado juan = new Empleado("juan");
		Empleado javier = new Empleado("javier");

		List<Empleado> allEmployees = empleadoServicio.obtenerTodos();
		verificarBusquedaTodosEmpleados();
		assertThat(allEmployees).hasSize(3).extracting(Empleado::getNombre).contains(pablo.getNombre(),
				juan.getNombre(), javier.getNombre());
	}

	private void verificarBusquePorNombreLamadaUnaVez(String nombre) {
		Mockito.verify(empleadoRepo, VerificationModeFactory.times(1)).findOneByNombre(nombre);
		Mockito.reset(empleadoRepo);
	}

	private void verificarBusquePorId() {
		Mockito.verify(empleadoRepo, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.reset(empleadoRepo);
	}

	private void verificarBusquedaTodosEmpleados() {
		Mockito.verify(empleadoRepo, VerificationModeFactory.times(1)).findAll();
		Mockito.reset(empleadoRepo);
	}

}
