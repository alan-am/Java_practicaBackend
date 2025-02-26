package com.dh.apirest_clinica.service.impl;

import com.dh.apirest_clinica.entity.Odontologo;
import com.dh.apirest_clinica.entity.Paciente;
import com.dh.apirest_clinica.entity.Turno;
import com.dh.apirest_clinica.repository.ITurnoRepository;
import com.dh.apirest_clinica.service.ITurnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class  TurnoService implements ITurnoService {
    private static final Logger log = LoggerFactory.getLogger(TurnoService.class);
    private PacienteService pacienteService;
    private OdontologoService odontologoService;
    private ITurnoRepository iTurnoRepository;

    public TurnoService(PacienteService pacienteService, OdontologoService odontologoService, ITurnoRepository iTurnoRepository) {
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.iTurnoRepository = iTurnoRepository;
    }

    @Override
    public Turno guardar(Turno turno){ // el turno viene solo con el id de paciente y odontologo
        Optional<Paciente> paciente = pacienteService.buscarPorID(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turno.getOdontologo().getId());
        Turno turnoARetornar = null;
        if (paciente.isPresent() && odontologo.isPresent() ) {
            turno.setPaciente(paciente.get()); //.get devuelve el tipo de dato dentro del opcional
            turno.setOdontologo(odontologo.get());
            turnoARetornar = iTurnoRepository.save(turno);
        }
        return turnoARetornar;
    }


    @Override
    public Optional<Turno> buscarPorId(Integer id) {
        return iTurnoRepository.findById(id);
    }

    @Override
    public List<Turno> buscarTodos() {
        return iTurnoRepository.findAll();
    }

    @Override
    public void modificarTurno(Turno turno) {
        Optional<Paciente> paciente = pacienteService.buscarPorID(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turno.getOdontologo().getId());
        if (paciente.isPresent() && odontologo.isPresent() ) {
            turno.setPaciente(paciente.get()); //.get devuelve el tipo de dato dentro del opcional
            turno.setOdontologo(odontologo.get());
            iTurnoRepository.save(turno); //persistimos el turno
        }
    }

    @Override
    public void eliminarTurno(Integer id) {
        iTurnoRepository.deleteById(id);
    }
}
