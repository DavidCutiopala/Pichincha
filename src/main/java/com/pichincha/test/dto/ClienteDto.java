package com.pichincha.test.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

	@NotBlank(message = "Nombre is required")
	private String nombre;

	@NotNull
	@NotBlank(message = "genero is required")
	private String genero;
	@NotNull
	@NotBlank(message = "edad is required")
	private int edad;

	@NotNull
	@NotBlank(message = "identificacion is required")
	@Size(min = 10, max = 12)
	private String identificacion;

	@NotNull
	@NotBlank(message = "identificacion is required")
	private String direccion;

	@NotNull
	@NotBlank(message = "telefono is required")
	private String telefono;

	@NotNull
	@NotBlank(message = "contrasena is required")
	private String contrasena;

	@NotNull
	@NotBlank(message = "estado is required")
	private String estado;

	@NotNull
	@NotBlank(message = "clienteid is required")
	private String clienteid;

}
