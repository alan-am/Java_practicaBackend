package com.dh.apirest_clinica.service;

import com.dh.apirest_clinica.dao.IDao;
import com.dh.apirest_clinica.model.Paciente;
import com.dh.apirest_clinica.dao.IDao;
import com.dh.apirest_clinica.model.Paciente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service   // Esta anotacion permite que Spring maneje por cuenta propia las instancias que necesite del servicio, sino lo tendriamos que hacer nosotros
public class PacienteService {
    private IDao<Paciente> pacienteIDao;
    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);

    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }


    /////////
    public Paciente guardarPaciente(Paciente paciente){
        return pacienteIDao.guardar(paciente);
    }
    public Paciente buscarPorID(Integer id){
        return pacienteIDao.buscarPorId(id);
    }
    public void borrarPorId(Integer id){
        pacienteIDao.borrarPorId(id);
    }
    public  void modificar(Paciente paciente){
        pacienteIDao.modificar(paciente);
    }
    public ArrayList<Paciente> buscarTodos(){
        return  pacienteIDao.buscarTodos();
    }

}
