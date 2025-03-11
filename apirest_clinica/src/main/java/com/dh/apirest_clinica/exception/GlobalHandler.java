package com.dh.apirest_clinica.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.List;

//manejador de excepciones
@ControllerAdvice
public class GlobalHandler {

    //armo mi respuestas cuando se lanze una excepcion
    @ExceptionHandler(ResourceNotFoundException.class) //se activa cuando se lanza este tipo de excepcion.
    public ResponseEntity<ApiError> handlerResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) { //request -> lo que viene desde el front
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(Exception.class) //maneja cualquier excepcion no contemplada
    public ResponseEntity<String> handlerAnyException(Exception e ){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
