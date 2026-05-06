package com.lafachada.agenda.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lafachada.agenda.Dto.AgendaRespuestaDto;
import com.lafachada.agenda.Service.AgendaService;

@RestController
@RequestMapping("api/v0/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping("/buscarPorIdCliente/{id}")
    public ResponseEntity<List<AgendaRespuestaDto>> buscarPorIdCliente(@PathVariable Integer id) {
        List<AgendaRespuestaDto> agendas = agendaService.buscarPorIdCliente(id);
        return ResponseEntity.ok(agendas);
    }

    @DeleteMapping("/eliminarPorId/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Integer id) {
        agendaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
