package com.lafachada.agenda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lafachada.agenda.Model.Agenda;

public interface AgendaRepository extends JpaRepository <Agenda, Integer>{
}
