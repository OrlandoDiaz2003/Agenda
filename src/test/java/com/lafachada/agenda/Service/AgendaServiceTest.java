package com.lafachada.agenda.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lafachada.agenda.Client.PublicacionClient;
import com.lafachada.agenda.Dto.AgendaRespuestaDto;
import com.lafachada.agenda.Dto.AgendaSolicitudDto;
import com.lafachada.agenda.Dto.PublicacionDto;
import com.lafachada.agenda.Mapper.AgendaMapper;
import com.lafachada.agenda.Model.Agenda;
import com.lafachada.agenda.Model.EstadoCita;
import com.lafachada.agenda.Repository.AgendaRepository;
import com.lafachada.agenda.Repository.EstadoCitaRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class AgendaServiceTest {

    @Mock
    private AgendaRepository agendaRepository;
    @Mock
    private PublicacionClient publicacionClient;
    @Mock
    private EstadoCitaRepository estadoCitaRepository;
    @Mock
    private AgendaMapper agendaMapper;

    @InjectMocks
    private AgendaService agendaService;

    @Test
    void cambiarEstadoCita_CuandoEstadoYAgendaExisten_DebeActualizarCorrectamente() {
        Integer idAgenda = 1;
        String estadoInput = " ACEPTADO ";
        String respuestaInput = "Nos vemos a las 15:00";

        EstadoCita estadoMock = new EstadoCita();
        estadoMock.setEstado("aceptado");

        Agenda agendaMock = new Agenda();
        agendaMock.setEstadoCita(estadoMock);

        when(estadoCitaRepository.findByEstado("aceptado")).thenReturn(Optional.of(estadoMock));
        when(agendaRepository.findById(idAgenda)).thenReturn(Optional.of(agendaMock));
        when(agendaRepository.save(any(Agenda.class))).thenReturn(agendaMock);

        AgendaRespuestaDto resultado = agendaService.cambiarEstadoCita(idAgenda, estadoInput, respuestaInput);

        assertNotNull(resultado);
        assertEquals("Nos vemos a las 15:00", agendaMock.getVendedorMensaje());
        verify(agendaRepository, times(1)).save(agendaMock);
    }

    @Test
    void cambiarEstadoCita_CuandoEstadoNoExiste_DebeLanzarEntityNotFoundException() {
        String estadoInvalido = "ESTADO_FALSO";
        when(estadoCitaRepository.findByEstado("estado_falso")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            agendaService.cambiarEstadoCita(1, estadoInvalido, "un mensaje");
        });

        verify(agendaRepository, never()).findById(any());
        verify(agendaRepository, never()).save(any());
    }

    @Test
    void crearCita_CuandoPublicacionNoExisteEnElOtroMicroservicio_DebeLanzarEntityNotFoundException() {
        AgendaSolicitudDto dto = new AgendaSolicitudDto();
        dto.setIdPublicacion(50);

        when(publicacionClient.obtenerPorId(50)).thenReturn(null);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            agendaService.crearCita(dto);
        });

        assertEquals("La publicacion no se ha encontrado", ex.getMessage());
        verify(estadoCitaRepository, never()).findById(any());
    }

    @Test
    void crearCita_CuandoPublicacionNoEstaDisponible_DebeLanzarIllegalStateException() {
        AgendaSolicitudDto dto = new AgendaSolicitudDto();
        dto.setIdPublicacion(50);

        PublicacionDto publicacionMock = new PublicacionDto();
        publicacionMock.setEstado("vendido");

        when(publicacionClient.obtenerPorId(50)).thenReturn(publicacionMock);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            agendaService.crearCita(dto);
        });

        assertEquals("La publicacion no se encuentra disponible", ex.getMessage());
        verify(estadoCitaRepository, never()).findById(any());
    }

    @Test
    void crearCita_FlujoExitoso_DebeGuardarYRetornar() {
        AgendaSolicitudDto dto = new AgendaSolicitudDto();
        dto.setIdPublicacion(50);

        PublicacionDto publicacionMock = new PublicacionDto();
        publicacionMock.setEstado("disponible");

        EstadoCita estadoPendiente = new EstadoCita();
        estadoPendiente.setEstado("pendiente");

        Agenda nuevaAgenda = new Agenda();
        nuevaAgenda.setEstadoCita(estadoPendiente);

        when(publicacionClient.obtenerPorId(50)).thenReturn(publicacionMock);
        when(estadoCitaRepository.findById(2)).thenReturn(Optional.of(estadoPendiente));
        when(agendaMapper.agendaBySolicitud(dto, estadoPendiente, publicacionMock)).thenReturn(nuevaAgenda);
        when(agendaRepository.save(nuevaAgenda)).thenReturn(nuevaAgenda);

        AgendaRespuestaDto resultado = agendaService.crearCita(dto);

        assertNotNull(resultado);
        verify(agendaRepository, times(1)).save(nuevaAgenda);
    }

    @Test
    void eliminarPorId_CuandoNoExiste_DebeLanzarEntityNotFoundException() {
        Integer id = 99;
        when(agendaRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            agendaService.eliminarPorId(id);
        });

        verify(agendaRepository, never()).deleteById(any());
    }
}