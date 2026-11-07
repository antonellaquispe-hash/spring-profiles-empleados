package com.techsolutions.empleados.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsolutions.empleados.model.Empleado;
import com.techsolutions.empleados.service.EmpleadoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({EmpleadoController.class, ConfiguracionController.class})
@ActiveProfiles("dev")
@TestPropertySource(properties = {
        "empresa.ambiente=DESARROLLO",
        "server.port=8080",
        "spring.datasource.url=jdbc:mysql://localhost:3306/empleados_dev",
        "spring.profiles.active=dev"
})
class EmpleadosControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private EmpleadoService service;

    private Empleado empleado(Long id) {
        return new Empleado(id, "Ana", "Pérez", "ana@example.com", new BigDecimal("2500.00"));
    }

    @Test
    void configuracionIncluyeAmbientePerfilPuertoYBaseDatos() throws Exception {
        mvc.perform(get("/configuracion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ambiente").value("DESARROLLO"))
                .andExpect(jsonPath("$.perfil").value("dev"))
                .andExpect(jsonPath("$.puerto").value("8080"))
                .andExpect(jsonPath("$.baseDatos").value("empleados_dev"));
    }

    @Test
    void crudRespondeConCodigosEsperados() throws Exception {
        when(service.crear(any(Empleado.class))).thenReturn(empleado(1L));
        when(service.listar()).thenReturn(Collections.singletonList(empleado(1L)));
        when(service.obtener(1L)).thenReturn(empleado(1L));
        when(service.actualizar(eq(1L), any(Empleado.class))).thenReturn(empleado(1L));

        String body = objectMapper.writeValueAsString(empleado(null));
        mvc.perform(post("/empleados").contentType("application/json").content(body))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1));
        mvc.perform(get("/empleados")).andExpect(status().isOk()).andExpect(jsonPath("$[0].email").value("ana@example.com"));
        mvc.perform(get("/empleados/1")).andExpect(status().isOk()).andExpect(jsonPath("$.nombres").value("Ana"));
        mvc.perform(put("/empleados/1").contentType("application/json").content(body))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1));
        mvc.perform(delete("/empleados/1")).andExpect(status().isNoContent());
        verify(service).eliminar(1L);
    }

    @Test
    void validaCamposObligatoriosEnCreacion() throws Exception {
        mvc.perform(post("/empleados").contentType("application/json").content("{}"))
                .andExpect(status().isBadRequest());
        verify(service, never()).crear(any(Empleado.class));
    }
    @Test
    void devuelve404CuandoElEmpleadoNoExiste() throws Exception {
        when(service.obtener(99L)).thenThrow(new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Empleado no encontrado: 99"));
        mvc.perform(get("/empleados/99" )).andExpect(status().isNotFound());
    }
}

