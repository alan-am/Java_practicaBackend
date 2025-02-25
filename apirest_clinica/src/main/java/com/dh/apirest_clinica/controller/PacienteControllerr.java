package com.dh.apirest_clinica.controller;


import com.dh.apirest_clinica.model.Paciente;
import com.dh.apirest_clinica.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    public Paciente buscarPorId(@PathVariable Integer id){
        return pacienteService.buscarPorID(id);
    }

    @GetMapping("/buscarTodos")
    public ArrayList<Paciente> buscarTodos(){
        return pacienteService.buscarTodos();
    }

    @PutMapping("/modificar")
    public String modificarPaciente(@RequestBody Paciente paciente){
        pacienteService.modificar(paciente);
        return "El paciente fue modificado"; //mensaje de estatus?
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Integer id){
        pacienteService.borrarPorId(id);
        return "Paciente eliminado";
    }


}
