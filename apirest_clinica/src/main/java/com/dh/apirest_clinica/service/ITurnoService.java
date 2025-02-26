package com.dh.apirest_clinica.service;

import com.dh.apirest_clinica.entity.Turno;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ITurnoService {

    public Turno guardar(Turno turno);
    public Optional<Turno> buscarPorId(Integer id);

    public List<Turno> buscarTodos();
    public void modificarTurno(Turno turno);

    public void eliminarTurno(Integer id);

}
