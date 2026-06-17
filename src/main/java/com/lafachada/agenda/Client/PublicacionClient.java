package com.lafachada.agenda.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lafachada.agenda.Dto.PublicacionDto;

@FeignClient(name = "publicacion-service", url = "${services.publicacion.url:http://localhost:8085/api/v1/publicacion}")
public interface PublicacionClient {

    @GetMapping("/{id}")
    PublicacionDto obtenerPorId(@PathVariable("id") Integer id);
}
