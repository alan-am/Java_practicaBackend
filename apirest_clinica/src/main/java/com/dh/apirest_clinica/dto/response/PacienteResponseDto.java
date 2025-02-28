package com.dh.apirest_clinica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteResponseDto {
    //solo lo necesario para el turno, si a lo mejor necesitamos para una busqueda exacta habria que crear uno nuevo para paciente
    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;

}
