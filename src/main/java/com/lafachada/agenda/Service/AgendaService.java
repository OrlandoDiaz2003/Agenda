package com.lafachada.agenda.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lafachada.agenda.Client.PropiedadClient;
import com.lafachada.agenda.Dto.AgendaRespuestaDto;
import com.lafachada.agenda.Dto.AgendaSolicitudDto;
import com.lafachada.agenda.Dto.PropiedadDto;
import com.lafachada.agenda.Model.Agenda;
import com.lafachada.agenda.Model.EstadoCita;
import com.lafachada.agenda.Repository.AgendaRepository;
import com.lafachada.agenda.Repository.EstadoCitaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private PropiedadClient propiedadClient;
    @Autowired
    private EstadoCitaRepository estadoCitaRepository;

    public List<AgendaRespuestaDto> buscarPorIdCliente(Integer id) {
        return agendaRepository.findByIdCliente(id).stream().map(AgendaRespuestaDto::new).toList();
    }

    public List<AgendaRespuestaDto> buscarPorIdVendedor(Integer id) {
        return agendaRepository.findByIdVendedor(id).stream().map(AgendaRespuestaDto::new).toList();
    }

    public void eliminarPorId(Integer id) {
        if (!agendaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se ha encontrada una agenda con id " + id);
        }
        agendaRepository.deleteById(id);
    }

    @Transactional
    public AgendaRespuestaDto crearCita(AgendaSolicitudDto dto) {
        PropiedadDto propiedad = propiedadClient.obtenerPorId(dto.getPropiedadId());

        if (propiedad == null) {
            throw new EntityNotFoundException("La propiedad no se ha encontrado");
        }

        if (!"disponible".equals(propiedad.getEstado())) {
            throw new IllegalStateException("La propiedad no se encuentra disponible");
        }
        Agenda nuevaAgenda = new Agenda();
        nuevaAgenda.setIdCliente(dto.getIdCliente());
        nuevaAgenda.setIdVendedor(dto.getIdVendedor());
        nuevaAgenda.setFecha(dto.getFecha());
        nuevaAgenda.setPropiedadId(propiedad.getPropiedadId());

        EstadoCita estadoInicial = estadoCitaRepository.findById(3)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado cita con id 3"));
        nuevaAgenda.setEstadoCita(estadoInicial);
        return new AgendaRespuestaDto(agendaRepository.save(nuevaAgenda));
    }
}