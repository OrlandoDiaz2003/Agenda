package com.lafachada.agenda.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lafachada.agenda.Model.EstadoCita;
import com.lafachada.agenda.Repository.EstadoCitaRepository;

@Component
public class DataInit implements CommandLineRunner {

    private final EstadoCitaRepository estadoCitaRepository;

    public DataInit(EstadoCitaRepository estadoCitaRepository) {
        this.estadoCitaRepository = estadoCitaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (estadoCitaRepository.count() == 0) {
            obtenerEstados();
            System.out.println("Estado de citas agregadas correctamente");
        }
    }

    private void obtenerEstados() {
        String[] Estados = { "completada", "pendiente", "aceptada", "cancelada"};

        for (String estado : Estados) {
            EstadoCita nuevoEstado = new EstadoCita();
            nuevoEstado.setEstado(estado);
            estadoCitaRepository.save(nuevoEstado);
        }
    }
}
