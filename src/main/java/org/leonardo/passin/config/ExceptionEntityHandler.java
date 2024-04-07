package org.leonardo.passin.config;

import org.leonardo.passin.domain.events.exceptions.EventNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleExceptionNotFound(EventNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
