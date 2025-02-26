package com.dh.apirest_clinica.service;

import com.dh.apirest_clinica.dao.IDao;
import com.dh.apirest_clinica.model.Odontologo;
import com.dh.apirest_clinica.model.Paciente;
import com.dh.apirest_clinica.model.Turno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TurnoService {
    private IDao<Turno> turnoIDao;
    private static final Logger log = LoggerFactory.getLogger(TurnoService.class);
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    public TurnoService(IDao<Turno> turnoIDao, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoIDao = turnoIDao;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    public Turno guardar(Turno turno){ // el turno viene solo con el id de paciente y odontologo
        Paciente paciente = pacienteService.buscarPorID(turno.getPaciente().getId());
        Odontologo odontologo = odontologoService.buscarPorId(turno.getOdontologo().getId());
        Turno turnoARetornar = null;
        if (paciente != null && odontologo != null) {
            turno.setPaciente(paciente);
            turno.setOdontologo(odontologo); //aca se le asigna al json la info completa de cada uno
            turnoARetornar = turnoIDao.guardar(turno);
        }
        return turnoARetornar;
    }
    public Turno buscarPorId(Integer id){
        return turnoIDao.buscarPorId(id);
    }

    public ArrayList<Turno> buscarTodos(){
        return turnoIDao.buscarTodos();
    }
    public void modificarTurno(Turno turno){
        turnoIDao.modificar(turno);
    }

    public void eliminarTurno(Integer id){
        turnoIDao.borrarPorId(id);
    }

}
