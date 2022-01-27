package com.prueba.servicio.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.modelo.Empleado;
import com.prueba.repositorio.IEmpleadoRepo;
import com.prueba.servicio.IEmpleadoServicio;

@Service
public class EmpleadoServicioImpl implements IEmpleadoServicio {

	@Autowired
	private IEmpleadoRepo empleadoRepo;

	@Override
	public Empleado guardar(Empleado empleado) {
		return empleadoRepo.save(empleado);
	}

	@Override
	public List<Empleado> obtenerTodos() {
		return empleadoRepo.findAll();
	}

	@Override
	public Empleado buscarPorNombre(String nombre) {
		return empleadoRepo.findOneByNombre(nombre);
	}

	@Override
	public boolean validarExisteEmpleado(String nombre) {
		return Objects.nonNull(empleadoRepo.findOneByNombre(nombre));
	}

	@Override
	public Empleado buscarPorId(Long id) {
		return empleadoRepo.findById(id).orElse(null);
	}

}
