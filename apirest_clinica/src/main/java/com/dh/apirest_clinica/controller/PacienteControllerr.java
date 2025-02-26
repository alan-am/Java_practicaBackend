package com.dh.apirest_clinica.controller;


import com.dh.apirest_clinica.entity.Paciente;
import com.dh.apirest_clinica.service.impl.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")  //ponemos la ruta desde donde surgira el endpoint(y damos orden en caso de tener mas controladores)
public class PacienteControllerr {
    private PacienteService pacienteService;

    public PacienteControllerr(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //Secuencia del POST(Serializacion y deserializacion de datos)
    //ingresa Json desde el front -> pasa automaticamente por la libreria Jackson -> y la libreria lo transforma en un objeto Paciente
    // sale como Objeto paciente(con id) -> pasa por el jackson -> lo transforma en Json

    //* la libreria jackson requiere de los getter ,setter y el constructor default(crea un objeto paciente vacio y con los get and set los va completando)
    @PostMapping("/guardar")  //endpoint para guardar un paciente
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente  paciente){    //@RequestBody yaq Los post llevan un body con la info del paciente
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente)); //devolvemos una respuesta de servidor por medio de responseEntity
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id){  //se le pone option pq existen diferentes tipos de retorno
        Optional<Paciente> paciente =  pacienteService.buscarPorID(id);
        if(paciente.isPresent()){
          return ResponseEntity.ok(paciente.get()); //no olvidar el .get
        }else{
           // String jsonResponse = "{\"mensaje\" : \"El paciente fue modificado\"}";   //!ojo
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado");

        }
    }
//    return ResponseEntity.notFound().build(); 1ra forma
//    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();   Otra forma q tiene mas opciones de respuestas
//    return ResponseEntity.status(HttpStatus.valueOf(404)).build();


    @GetMapping("/buscarTodos")
    public ResponseEntity<List<Paciente>> buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    //Las validaciones deberian ir en el service pero para este caso la haremos en el controller hasta conocer DTO
    @PutMapping("/modificar")
    public ResponseEntity<?> modificarPaciente(@RequestBody Paciente paciente){
        //buscamos al paciente primero para saber si existe
        Optional<Paciente> pacienteInvolucrado = pacienteService.buscarPorID(paciente.getId());
        if(pacienteInvolucrado.isPresent()){
            pacienteService.modificar(paciente);
            String jsonResponse = "{\"mensaje\" : \"El paciente fue modificado\"}";
            return ResponseEntity.ok(jsonResponse);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Integer id){
        Optional<Paciente> pacienteInvolucrado = pacienteService.buscarPorID(id);
        if(pacienteInvolucrado.isPresent()){
            pacienteService.borrarPorId(id);
            String jsonResponse = "{\"mensaje\" : \"El paciente fue eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
