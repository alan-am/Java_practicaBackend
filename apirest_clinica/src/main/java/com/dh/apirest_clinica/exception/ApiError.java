package com.dh.apirest_clinica.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

//clase para describir mejor el punto de un error en la app
@Getter
@Setter
@AllArgsConstructor
public class ApiError {
    private String path;
    private String mensaje;
    private int statuCode;
    private ZonedDateTime zonedDateTime;
    private List<String> errores; //almacenar errores

}

