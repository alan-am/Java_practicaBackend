package com.dh.apirest_clinica.service.impl;

import com.dh.apirest_clinica.entity.Paciente;
import com.dh.apirest_clinica.repository.IPacienteRepository;
import com.dh.apirest_clinica.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private IPacienteRepository iPacienteRepository; //igual que con dao

    public PacienteService(IPacienteRepository iPacienteRepository) {
        this.iPacienteRepository = iPacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        return iPacienteRepository.save(paciente);
    }

    @Override
    public Optional<Paciente> buscarPorID(Integer id) {
        return iPacienteRepository.findById(id); //devuelve un Optional
    }

    @Override
    public void borrarPorId(Integer id) {
        iPacienteRepository.deleteById(id);
    }

    @Override
    public void modificar(Paciente paciente) {
        iPacienteRepository.save(paciente); //el mismo que guardar
    }

    @Override
    public List<Paciente> buscarTodos() {
        return iPacienteRepository.findAll();
    }

    /////////

}
