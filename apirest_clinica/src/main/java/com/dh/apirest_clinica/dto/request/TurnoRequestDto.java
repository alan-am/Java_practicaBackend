package com.dh.apirest_clinica.dto.request;

//paquete de entrada de datos

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoRequestDto {
    //Dto que va recibir la informacion del turno, como si fuera una clase Plana de la entidad y los atributos q esperamos recibir
    private Integer paciente_id;
    private Integer odontologo_id;
    private String fecha;  //no Local date para tratar de mantener lo mas planos posibles
}
