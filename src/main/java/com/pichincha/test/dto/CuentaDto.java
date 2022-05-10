package com.pichincha.test.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDto {

	private Long idCuenta;
	private String numeroCuenta;
	private String tipoCuenta;
	private BigDecimal saldoInicial;
	private String estado;
	private String clienteId;

}
