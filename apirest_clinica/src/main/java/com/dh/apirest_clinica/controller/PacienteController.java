package com.dh.apirest_clinica.controller;


import com.dh.apirest_clinica.model.Paciente;
import com.dh.apirest_clinica.service.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller  //indica que se trabajara con una vista interna  a diferencia de @RestController que indica lo contrario
//ademas,  Esta anotacion permite que Spring maneje por cuenta propia las instanciacion de este controlador
public class PacienteController {

    PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    //opciones de recibir un parametro:
    // 1)  localhost:8080/1   -> @PathVariable       Usualmente usado como cuando se clickea un producto
    // 2)  localhost:8080?id=1  -> @RequestParams    Usualmente usado como un search con filtro

    @GetMapping("/index")
    public String mostrarPacientePorId(Model model, @RequestParam Integer id){ //como me llega este id? por medio de una solicitud pero primero defino en cual de las 2 formas lo recibo
        Paciente paciente =  pacienteService.buscarPorID(id); //ya el paciente encontrado se lo debemos pasar a la vista
        model.addAttribute("nombrePaciente", paciente.getNombre()); //recibe -> el nombre de la variable definida en la vista Y que valor va llevar.
        model.addAttribute("apellidoPaciente", paciente.getApellido());
        return "paciente"; //aqui se retorna el nombre de la vista
    }

    @GetMapping("/index2/{id}") // le debo especificar como se va recibir el parametro
    public String mostrarPacientePorId2(Model model, @PathVariable Integer id){
        Paciente paciente =  pacienteService.buscarPorID(id);
        model.addAttribute("nombrePaciente", paciente.getNombre());
        model.addAttribute("apellidoPaciente", paciente.getApellido());
        return "paciente";
    }
}

//la interfaz model nos ayudara a poder pasarle los datos del controlador a la vista.
//Se resalta que en este caso la pagina web 'paciente' esta ejecutada del lado del servidor , no es un front