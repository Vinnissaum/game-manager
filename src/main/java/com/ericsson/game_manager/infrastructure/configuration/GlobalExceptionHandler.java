package com.ericsson.game_manager.infrastructure.configuration;

import com.ericsson.game_manager.domain.exceptions.DomainException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DomainException.class})
    public ResponseEntity<?> handleDomainException(DomainException ex) {
        return new ResponseEntity<>(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }
}
