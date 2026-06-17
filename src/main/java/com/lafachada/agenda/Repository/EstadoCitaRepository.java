package com.lafachada.agenda.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lafachada.agenda.Model.EstadoCita;

@Repository
public interface EstadoCitaRepository extends JpaRepository<EstadoCita, Integer> {

    Optional<EstadoCita> findByEstado(String estado);
}
