package com.dh.apirest_clinica.controller;


import com.dh.apirest_clinica.dto.request.TurnoModificarDto;
import com.dh.apirest_clinica.dto.request.TurnoRequestDto;
import com.dh.apirest_clinica.dto.response.TurnoResponseDto;
import com.dh.apirest_clinica.entity.Paciente;
import com.dh.apirest_clinica.entity.Turno;
import com.dh.apirest_clinica.service.impl.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService;
    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarTurno(@RequestBody TurnoRequestDto turnoRequestDto){
        TurnoResponseDto turnoAGuardar = turnoService.guardar(turnoRequestDto); //ahora como hago el mapeo(conversion) del dto a la entidad, esto se hace en el service
        if(turnoAGuardar != null){
            return ResponseEntity.ok(turnoAGuardar);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente o el odontologo no fueron encontrados");
        }

    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificarTurno(@RequestBody TurnoModificarDto turnoModificarDto){
        //buscamos al paciente primero para saber si existe
        Optional<TurnoResponseDto> turnoInvolucrado = turnoService.buscarPorId(turnoModificarDto.getId());
        if(turnoInvolucrado.isPresent()){
            turnoService.modificarTurno(turnoModificarDto);
            String jsonResponse = "{\"mensaje\" : \"El Turno fue modificado\"}";
            return ResponseEntity.ok(jsonResponse);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id){
        turnoService.eliminarTurno(id);
        String jsonResponse = "{\"mensaje\" : \"El Turno fue eliminado\"}";
        return ResponseEntity.ok(jsonResponse);
    }
}
