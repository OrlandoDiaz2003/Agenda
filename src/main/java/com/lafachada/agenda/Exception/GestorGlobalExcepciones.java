package com.lafachada.agenda.Exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GestorGlobalExcepciones {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> manejarEntidadNoEncontrada(EntityNotFoundException ex) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("fecha", LocalDateTime.now());
        cuerpo.put("estado", HttpStatus.NOT_FOUND);
        cuerpo.put("error", ex.getMessage());
        return new ResponseEntity<>(cuerpo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> manejarFeignException(FeignException ex) {
        String cuerpoError = ex.contentUTF8();

        int status = ex.status() != -1 ? ex.status() : 500;
        if (cuerpoError == null || cuerpoError.isEmpty()) {
            cuerpoError = "{\"error\": \"Error de comunicación con el servicio externo\"}";
        }
        return ResponseEntity
                .status(status)
                .header("Content-Type", "application/json")
                .body(cuerpoError);
    }
}