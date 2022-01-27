package com.prueba.servicio;

import java.util.List;

import com.prueba.modelo.Empleado;

public interface IEmpleadoServicio {
	Empleado guardar(Empleado empleado);

	List<Empleado> obtenerTodos();

	Empleado buscarPorNombre(String nombre);
	
	boolean validarExisteEmpleado(String nombre);
	
	Empleado buscarPorId(Long id);
}
