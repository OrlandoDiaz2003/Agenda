package com.lafachada.agenda.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lafachada.agenda.Dto.PropiedadDto;

@FeignClient(name = "propiedad-service", url = "${services.propiedad.url:http://localhost:8081/api/v0/propiedad}")
public interface PropiedadClient {

    @GetMapping("/obtenerPorId/{id}")
    PropiedadDto obtenerPorId(@PathVariable("id") Integer id);
}
