package com.lafachada.agenda.Mapper;

import org.springframework.stereotype.Component;

import com.lafachada.agenda.Dto.AgendaSolicitudDto;
import com.lafachada.agenda.Dto.PublicacionDto;
import com.lafachada.agenda.Model.Agenda;
import com.lafachada.agenda.Model.EstadoCita;

@Component
public class AgendaMapper {

    public Agenda agendaBySolicitud(AgendaSolicitudDto dto, EstadoCita estadoCita, PublicacionDto publicacion) {
        Agenda nuevaAgenda = new Agenda();
        nuevaAgenda.setIdCliente(dto.getIdCliente());
        nuevaAgenda.setIdVendedor(dto.getIdVendedor());
        nuevaAgenda.setFecha(dto.getFecha());

        if(dto.getClienteMensaje() != null && !dto.getClienteMensaje().isBlank()) {
            nuevaAgenda.setClienteMensaje(dto.getClienteMensaje());
        }

        nuevaAgenda.setIdPublicacion(publicacion.getIdPublicacion());
        nuevaAgenda.setEstadoCita(estadoCita);
        return nuevaAgenda;
    }

}
