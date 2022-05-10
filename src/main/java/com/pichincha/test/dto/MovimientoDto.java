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
public class MovimientoDto {
	private Long idMovimiento;
	private String fecha;
	private String tipoMovimiento;
	private BigDecimal valor;
	private BigDecimal saldo;
	private Long idCuenta;

}
