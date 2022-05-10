package com.pichincha.test.controllers;

import java.text.ParseException;
import java.util.List;

import com.pichincha.test.dto.ReporteDto;
import com.pichincha.test.services.ReporteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/reportes")
public class ReporteController {

	@Autowired
	private ReporteService reporteService;

	@ApiOperation(value = "obtener reporte.")
	@GetMapping("/get")
	public List<ReporteDto> registarMovimientos(@ApiParam("fechaInicio") @RequestParam String fechaInicio,
			@ApiParam("fechaFin") @RequestParam String fechaFin,
			@ApiParam("clienteId") @RequestParam String clienteId) throws ParseException {
		return reporteService.generarReporte(fechaInicio, fechaFin, clienteId);
	}

}
