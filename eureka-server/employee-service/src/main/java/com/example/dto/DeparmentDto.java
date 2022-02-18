package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeparmentDto {
	private Long id;
	private String name;
}
