package com.pichincha.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.test.dto.MovimientoDto;
import com.pichincha.test.entities.Movimiento;
import com.pichincha.test.repositories.MovimientoRepository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovimientoControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        MovimientoRepository movimientoRepository;

        @Test
        void crearMovimiento() throws Exception {
                MovimientoDto movimientoDto = new MovimientoDto(new Long(1), "08/02/2022 10:14:11", "CREDITO",
                                new BigDecimal(200), new BigDecimal(1000), new Long(2));
                mockMvc.perform(put("/api/movimientos/put")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(movimientoDto)))
                                .andExpect(status().isCreated());
        }

        @Test
        void modificarMovimiento() throws Exception {
                Movimiento movimiento = movimientoRepository.findTopByOrderByIdMovimientoDesc();

                MovimientoDto movimientoDto = new MovimientoDto(movimiento.getIdMovimiento(), "08/02/2022 10:14:11",
                                "CREDITO",
                                new BigDecimal(100), new BigDecimal(1000), new Long(2));
                mockMvc.perform(post("/api/movimientos/post")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(movimientoDto)))
                                .andExpect(status().isCreated());

                assertThat(movimientoDto.getIdMovimiento()).isEqualTo(movimiento.getIdMovimiento());
        }

        @Test
        void obtenerMovimiento() throws Exception {
                Movimiento movimiento = movimientoRepository.findTopByOrderByIdMovimientoDesc();

                mockMvc.perform(get("/api/movimientos/get/" + movimiento.getIdMovimiento())
                                .accept(MediaType.APPLICATION_JSON))
                                .andDo(print())
                                .andExpect(status().isOk());

        }

        @Test
        void eliminarMovimiento() throws Exception {
                Movimiento movimiento = movimientoRepository.findTopByOrderByIdMovimientoDesc();

                mockMvc.perform(delete("/api/movimientos/delete/" + movimiento.getIdMovimiento()))
                                .andExpect(status().isCreated());
        }

        @Test
        void registrarMovimiento() throws Exception {
                MovimientoDto movimientoDto = new MovimientoDto(new Long(1), "08/02/2022 10:14:11", "CREDITO",
                                new BigDecimal(200), new BigDecimal(1000), new Long(2));
                mockMvc.perform(post("/api/movimientos/registerMovements")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(movimientoDto)))
                                .andExpect(status().isCreated());
        }
}
