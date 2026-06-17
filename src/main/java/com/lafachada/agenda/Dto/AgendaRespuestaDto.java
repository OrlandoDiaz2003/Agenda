package com.lafachada.agenda.Dto;

import java.time.LocalDate;

import com.lafachada.agenda.Model.Agenda;
import lombok.Data;

@Data
public class AgendaRespuestaDto {
    private Integer IdAgenda;
    private Integer idVendedor;
    private Integer idCliente;
    private Integer idPublicacion;
    private LocalDate fecha;
    private String estadoCita;
    private String clienteMensaje;
    private String vendedorMensaje;

    public AgendaRespuestaDto(Agenda agenda) {
        this.IdAgenda = agenda.getIdAgenda();
        this.idVendedor = agenda.getIdVendedor();
        this.idCliente = agenda.getIdCliente();
        this.idPublicacion = agenda.getIdPublicacion();
        this.fecha = agenda.getFecha();
        this.estadoCita = agenda.getEstadoCita().getEstado();
        this.clienteMensaje = agenda.getClienteMensaje();
        this.vendedorMensaje = agenda.getVendedorMensaje();
    }
}
