package com.lafachada.agenda.Dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AgendaSolicitudDto {
    private Integer propiedadId;
    private Integer idCliente;
    private Integer idVendedor;
    private LocalDate fecha;
    private Integer idEstadoCita;
}
