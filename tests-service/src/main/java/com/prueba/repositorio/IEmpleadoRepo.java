package com.prueba.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.modelo.Empleado;

public interface IEmpleadoRepo  extends JpaRepository<Empleado, Long>{

	Empleado findOneByNombre(String nombre);
}
