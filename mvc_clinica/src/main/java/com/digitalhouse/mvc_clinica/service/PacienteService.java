package com.digitalhouse.mvc_clinica.service;

import com.digitalhouse.mvc_clinica.model.Paciente;
import com.digitalhouse.mvc_clinica.dao.IDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service   // Esta anotacion permite que Spring maneje por cuenta propia las instancias que necesite del servicio, sino lo tendriamos que hacer nosotros
public class PacienteService {
    private IDao<Paciente> pacienteIDao;
    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);

    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente guardarPaciente(Paciente paciente){
        return pacienteIDao.guardar(paciente);
    }
    public Paciente buscarPorID(Integer id){
        return pacienteIDao.buscarPorId(id);
    }
    public Paciente borrarPorId(Integer id){
        return pacienteIDao.borrarPorId(id);
    }

}
