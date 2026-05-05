package com.lafachada.agenda.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lafachada.agenda.Dto.AgendaRespuestaDto;
import com.lafachada.agenda.Repository.AgendaRepository;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public List<AgendaRespuestaDto> buscarPorIdCliente(Integer id) {
        return agendaRepository.findByIdCliente(id).stream().map(AgendaRespuestaDto::new).toList();
    }

}