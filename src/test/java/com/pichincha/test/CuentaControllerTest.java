package com.pichincha.test;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.test.dto.CuentaDto;
import com.pichincha.test.entities.Cuenta;
import com.pichincha.test.repositories.CuentaRepository;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CuentaControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        CuentaRepository cuentaRepository;

        @Test
        void crearCuenta() throws Exception {
                CuentaDto cuentaDto = new CuentaDto(new Long(2), "nc1", "tc1", new BigDecimal(200), "ACTIVO", "2");
                mockMvc.perform(put("/api/cuentas/put")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(cuentaDto)))
                                .andExpect(status().isCreated());

                Cuenta cuenta = cuentaRepository.findByIdCuenta(Long.valueOf(2));
                assertThat(cuenta.getEstado()).isEqualTo("ACTIVO");
        }

        @Test
        void modificarCuenta() throws Exception {
                Cuenta cuenta = cuentaRepository.findTopByOrderByIdCuentaDesc();
                CuentaDto cuentaDto = new CuentaDto(cuenta.getIdCuenta(), "nc2", "tc2", new BigDecimal(200), "ACTIVO",
                                "2");
                mockMvc.perform(post("/api/cuentas/post")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(cuentaDto)))
                                .andExpect(status().isOk());

                assertThat(cuentaDto.getIdCuenta()).isEqualTo(cuenta.getIdCuenta());
        }

        @Test
        void obtenerCuenta() throws Exception {
                Cuenta cuenta = cuentaRepository.findTopByOrderByIdCuentaDesc();
                mockMvc.perform(get("/api/cuentas/get/" + cuenta.getIdCuenta())
                                .accept(MediaType.APPLICATION_JSON))
                                .andDo(print())
                                .andExpect(status().isOk());

        }

        @Test
        void eliminarCuenta() throws Exception {
                Cuenta cuenta = cuentaRepository.findTopByOrderByIdCuentaDesc();
                mockMvc.perform(delete("/api/cuentas/delete/" + cuenta.getIdCuenta()))
                                .andExpect(status().isOk());
        }

}
