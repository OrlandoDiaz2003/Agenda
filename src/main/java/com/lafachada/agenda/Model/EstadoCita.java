package com.lafachada.agenda.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "estado_cita")
@Data
public class EstadoCita {
    /*
     * 1. Agendada
     * 2. Cancelada
     * 3. Pendiente
     */
    @Id
    @Column(name = "id_estado_cita")
    private Integer idEstadoCita;
    @Column(name = "estado", nullable =  false, unique = true)
    private String estado;
    @OneToMany(mappedBy = "estadoCita")
    private List<Agenda> agendas;
}
