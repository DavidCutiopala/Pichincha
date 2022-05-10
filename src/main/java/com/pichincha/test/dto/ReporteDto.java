package com.pichincha.test.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDto {
	@JsonProperty("Fecha")
	private String fecha;
	@JsonProperty("Cliente")
	private String cliente;
	@JsonProperty("Numero Cuenta")
	private String numeroCuenta;
	@JsonProperty("Tipo")
	private String tipo;
	@JsonProperty("Saldo Inicial")
	private BigDecimal saldoInicial;
	@JsonProperty("Estado")
	private boolean estado;
	@JsonProperty("Movimiento")
	private BigDecimal movimiento;
	@JsonProperty("Saldo Disponible")
	private BigDecimal saldoDisponible;

}
