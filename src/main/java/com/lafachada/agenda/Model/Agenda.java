package com.lafachada.agenda.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agenda")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agenda {
    @Id
    @Column(name = "id_agenda")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAgenda;

    @Column(name = "id_publicacion", nullable = false)
    private Integer idPublicacion;

    @Column(name = "id_vendedor", nullable = false)
    private Integer idVendedor;

    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "cliente_mensaje", nullable = true)
    private String clienteMensaje;

    @Column(name = "vendedor_mensaje", nullable =  true)
    private String vendedorMensaje;

    @ManyToOne
    @JoinColumn(name = "id_estado_cita", nullable = false)
    private EstadoCita estadoCita;

}