package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Fist name is blank")
	@Size(min = 5, max = 50, message = "First name must be between 5 and 50 characters")
	@Column(name = "first_name")
	private String firstName;

	@NotBlank(message = "Fist name is blank")
	@Size(min = 5, max = 100, message = "Last name must be between 5 and 100 characters")
	@Column(name = "last_name")
	private String lastName;
}
