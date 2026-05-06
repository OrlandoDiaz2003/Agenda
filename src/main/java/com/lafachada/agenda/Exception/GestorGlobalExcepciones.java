package com.lafachada.agenda.Exception;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GestorGlobalExcepciones {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> manejarEntidadNoEncontrada(EntityNotFoundException ex) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("Error", HttpStatus.NOT_FOUND);
        cuerpo.put("Mensaje", ex.getMessage());
        cuerpo.put("Fecha", LocalDate.now());
        return new ResponseEntity<>(cuerpo, HttpStatus.NOT_FOUND);
    }

}
