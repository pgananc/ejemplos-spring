package com.example.redis.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Employee")
@Builder
public class Employee implements Serializable {

	private static final long serialVersionUID = 441172926815428004L;
	@Id
	private Long id;
	private String firstName;
	private String lastName;
	private Boolean state;
}
