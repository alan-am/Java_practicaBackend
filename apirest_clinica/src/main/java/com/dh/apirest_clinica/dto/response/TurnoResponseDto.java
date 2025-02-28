package com.dh.apirest_clinica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// nunca se debe involucrar a la entidad en el dto
public class TurnoResponseDto {
    //devolvemos turno con id
    private Integer id;
    //devolvemos info del paciente
    private PacienteResponseDto pacienteResponseDto;
    //devolvemos info del odontologo
    private OdontologoResponseDto odontologoResponseDto;
    //Devolvemos fecha del turno
    private String fecha;
}









/* Json si usamos getters y setters directos de la entidad de Entidades(queda raro)
{
    id:
    pacienteNombre:
    pacienteApellido:
    odontologoNombre:
    odontologoApellido:
}
 */

/* Json si usamos un Dto de la entidad propia
{
    id:
    paciente:{
        nombre:
        apellido:
    },
    odontologo;{
        nombre:
        apellido:
    }
}
 */
