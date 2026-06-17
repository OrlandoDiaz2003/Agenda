package com.lafachada.agenda.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "estado_cita")
@Data
public class EstadoCita {
    /*
     * 1. completada
     * 2. pendiente
     * 3. aceptada
     * 4. cancelada
     */
    @Id
    @Column(name = "id_estado_cita")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstadoCita;

    @Column(name = "estado", nullable =  false, unique = true)
    private String estado;

    @OneToMany(mappedBy = "estadoCita")
    private List<Agenda> agendas;
}
