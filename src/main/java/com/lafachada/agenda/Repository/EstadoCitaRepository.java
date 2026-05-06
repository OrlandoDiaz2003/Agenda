package com.lafachada.agenda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lafachada.agenda.Model.EstadoCita;

public interface EstadoCitaRepository extends JpaRepository<EstadoCita, Integer> {

    EstadoCita findByEstado(String estado);
}
