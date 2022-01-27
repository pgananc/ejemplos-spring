package com.prueba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.modelo.Empleado;
import com.prueba.servicio.IEmpleadoServicio;

@RestController
@RequestMapping("api")
public class EmpleadoController {

	@Autowired
	private IEmpleadoServicio empleadoServicio;

	@PostMapping("empleados")
	public ResponseEntity<Empleado> guadar(@Validated @RequestBody Empleado empleado) {
		Empleado empleadoGuardado = empleadoServicio.guardar(empleado);
		return new ResponseEntity<Empleado>(empleadoGuardado, HttpStatus.CREATED);
	}

	@GetMapping("empleados")
	public List<Empleado> obtenerTodos() {
		return empleadoServicio.obtenerTodos();
	}
}
