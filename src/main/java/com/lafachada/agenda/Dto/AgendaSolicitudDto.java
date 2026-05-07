package com.lafachada.agenda.Dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgendaSolicitudDto {
    @NotNull(message = "La cita debe tener una propiedad asociada")
    private Integer propiedadId;

    @NotNull(message = "La cita debe tener un vendedor asociado")
    private Integer idVendedor;

    @FutureOrPresent(message = "La fecha no puede ser en el pasado")
    private LocalDate fecha;

    @NotNull(message = "La cita tiene que tener un estado")
    private Integer idEstadoCita;

    private Integer idCliente;
}
