package com.lafachada.agenda.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lafachada.agenda.Dto.AgendaRespuestaDto;
import com.lafachada.agenda.Repository.AgendaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public List<AgendaRespuestaDto> buscarPorIdCliente(Integer id) {
        return agendaRepository.findByIdCliente(id).stream().map(AgendaRespuestaDto::new).toList();
    }

    public void eliminarPorId(Integer id) {
        if(!agendaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se ha encontrada una agenda con id " + id);
        }
        agendaRepository.deleteById(id);
    }
}