package com.prueba.modelo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "employee")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "firstName", nullable = false)
	@Size(min = 3, max = 20)
	@NonNull
	@NotEmpty(message = "'firstName' field was empty")
	private String firstName;

	@Column(name = "lastName", nullable = false)
	@Size(min = 3, max = 20)
	@NonNull
	@NotEmpty(message = "'lastName' field was empty")
	private String lastName;

	@Column(name = "birthDate", nullable = false)
	private LocalDate birthDate;

}
