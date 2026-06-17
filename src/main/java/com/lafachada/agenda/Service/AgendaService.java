package com.lafachada.agenda.Service;

import java.util.List;

import org.springframework.stereotype.Service;

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

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final PublicacionClient publicacionClient;
    private final EstadoCitaRepository estadoCitaRepository;
    private final AgendaMapper agendaMapper;

    public AgendaService(AgendaRepository agendaRepository, PublicacionClient publicacionClient,
            EstadoCitaRepository estadoCitaRepository, AgendaMapper agendaMapper) {
        this.agendaRepository = agendaRepository;
        this.publicacionClient = publicacionClient;
        this.estadoCitaRepository = estadoCitaRepository;
        this.agendaMapper = agendaMapper;
    }

    private static final Integer PENDIENTE = 2;

    public List<AgendaRespuestaDto> buscarPorIdCliente(Integer id) {
        return agendaRepository.findByIdCliente(id).stream().map(AgendaRespuestaDto::new).toList();
    }

    public List<AgendaRespuestaDto> buscarPorIdVendedor(Integer id) {
        return agendaRepository.findByIdVendedor(id).stream().map(AgendaRespuestaDto::new).toList();
    }

    public List<Integer> publicacionIdAgendas(Integer id) {
        return agendaRepository.findIdPublicacionByIdClienteAndEstadoCita(id, PENDIENTE);
    }

    public void eliminarPorId(Integer id) {
        if (!agendaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se ha encontrada una agenda con id " + id);
        }
        agendaRepository.deleteById(id);
    }

    public AgendaRespuestaDto cambiarEstadoCita(Integer idAgenda, String estado, String respuesta) {
        String estadoNormalizado = (estado != null && !estado.isEmpty()) ? estado.trim().toLowerCase() : null;

        EstadoCita nuevoEstado = estadoCitaRepository.findByEstado(estadoNormalizado)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el estado: " + estadoNormalizado));

        Agenda agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new EntityNotFoundException("Error al buscar agenda de id: " + idAgenda));
        agenda.setEstadoCita(nuevoEstado);

        if(respuesta !=  null && !respuesta.isBlank()) {
            agenda.setVendedorMensaje(respuesta);
        }

        agendaRepository.save(agenda);
        AgendaRespuestaDto respuestaDto = new AgendaRespuestaDto(agenda);
        return respuestaDto;
    }


    public AgendaRespuestaDto crearCita(AgendaSolicitudDto dto) {
        PublicacionDto publicacion = publicacionClient.obtenerPorId(dto.getIdPublicacion());
        if (publicacion == null) {
            throw new EntityNotFoundException("La publicacion no se ha encontrado");
        }

        if (!"disponible".equals(publicacion.getEstado())) {
            throw new IllegalStateException("La publicacion no se encuentra disponible");
        }
        return guardarCitaEnDb(dto, publicacion);
    }

    public AgendaRespuestaDto guardarCitaEnDb(AgendaSolicitudDto dto, PublicacionDto publicacion) {
        EstadoCita estadoInicial = estadoCitaRepository.findById(PENDIENTE)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado cita con id 3"));

        Agenda nuevaAgenda = agendaMapper.agendaBySolicitud(dto, estadoInicial, publicacion);
        return new AgendaRespuestaDto(agendaRepository.save(nuevaAgenda));
    }
}