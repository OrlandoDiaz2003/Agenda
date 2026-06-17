package com.lafachada.agenda.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lafachada.agenda.Model.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
    List<Agenda> findByIdCliente(Integer idCliente);

    List<Agenda> findByIdVendedor(Integer idVendedor);

    @Query("SELECT a.idPublicacion FROM Agenda a WHERE a.idCliente = :idCliente AND a.estadoCita.idEstadoCita = :estado")
    List<Integer> findIdPublicacionByIdClienteAndEstadoCita(@Param("idCliente") Integer idCliente, @Param("estado") Integer estado);
}
