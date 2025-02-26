package com.dh.apirest_clinica.service;

import com.dh.apirest_clinica.entity.Paciente;
import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    Paciente guardarPaciente(Paciente paciente);
    Optional<Paciente> buscarPorID(Integer id);
    void borrarPorId(Integer id);
    void modificar(Paciente paciente);
    List<Paciente> buscarTodos();
}
