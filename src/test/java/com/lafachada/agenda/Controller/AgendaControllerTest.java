package com.lafachada.agenda.Controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lafachada.agenda.Dto.AgendaRespuestaDto;
import com.lafachada.agenda.Service.AgendaService;

@WebMvcTest(AgendaController.class)
class AgendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AgendaService agendaService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String BASE_URL = "/api/v0/agenda";

    @Test
    void buscarPorIdCliente_DebeRetornarListaY200Ok() throws Exception {
        Integer idCliente = 1;
        List<AgendaRespuestaDto> listaMock = List.of(new AgendaRespuestaDto(), new AgendaRespuestaDto());

        when(agendaService.buscarPorIdCliente(idCliente)).thenReturn(listaMock);

        mockMvc.perform(get(BASE_URL + "/cliente/{id}", idCliente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void publicacionIdAgendas_DebeRetornarListaDeEnterosY200Ok() throws Exception {
        Integer idCliente = 1;
        List<Integer> idsMock = List.of(101, 102);

        when(agendaService.publicacionIdAgendas(idCliente)).thenReturn(idsMock);

        mockMvc.perform(get(BASE_URL + "/cliente/{id}/publicaciones-pendientes", idCliente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(101))
                .andExpect(jsonPath("$[1]").value(102));
    }

    @Test
    void buscarPorIdVendedor_DebeRetornarListaY200Ok() throws Exception {
        Integer idVendedor = 2;
        List<AgendaRespuestaDto> listaMock = List.of(new AgendaRespuestaDto());

        when(agendaService.buscarPorIdVendedor(idVendedor)).thenReturn(listaMock);

        mockMvc.perform(get(BASE_URL + "/vendedor/{id}", idVendedor))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarPorId_DebeRetornar24NoContent() throws Exception {
        Integer idAgenda = 5;
        doNothing().when(agendaService).eliminarPorId(idAgenda);

        mockMvc.perform(delete(BASE_URL + "/{id}", idAgenda))
                .andExpect(status().isNoContent());
    }

    @Test
    void cambiarEstado_ConParametrosRequeridos_DebeRetornar200Ok() throws Exception {
        Integer idAgenda = 1;
        String estado = "aceptado";
        String respuesta = "Confirmado el día de mañana";
        AgendaRespuestaDto respuestaDto = new AgendaRespuestaDto();

        when(agendaService.cambiarEstadoCita(eq(idAgenda), eq(estado), eq(respuesta))).thenReturn(respuestaDto);

        mockMvc.perform(put(BASE_URL + "/{id}/estado", idAgenda)
                .param("estado", estado)
                .param("respuesta", respuesta)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}