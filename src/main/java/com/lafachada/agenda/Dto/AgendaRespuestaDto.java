package com.lafachada.agenda.Dto;

import java.time.LocalDate;

import com.lafachada.agenda.Model.Agenda;
import lombok.Data;

@Data
public class AgendaRespuestaDto {
    private Integer idVendedor;
    private Integer idCliente;
    private Integer idPropiedad;
    private LocalDate fecha;
    private String estadoCita;

    public AgendaRespuestaDto(Agenda agenda) {
        this.idVendedor  = agenda.getIdVendedor();
        this.idCliente   = agenda.getIdCliente();
        this.idPropiedad = agenda.getPropiedadId();
        this.fecha       = agenda.getFecha();
        this.estadoCita  = agenda.getEstadoCita().getEstado();
    }

}
