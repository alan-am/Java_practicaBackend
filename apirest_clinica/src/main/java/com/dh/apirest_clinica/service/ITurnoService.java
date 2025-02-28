package com.dh.apirest_clinica.service;

import com.dh.apirest_clinica.dto.request.TurnoModificarDto;
import com.dh.apirest_clinica.dto.request.TurnoRequestDto;
import com.dh.apirest_clinica.dto.response.TurnoResponseDto;
import com.dh.apirest_clinica.entity.Turno;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ITurnoService {

    public TurnoResponseDto guardar(TurnoRequestDto turnoRequestDto); //recibo un turno response, dentro del servicio lo mapeo a turno , lo opero, persisto, y ese turno lo mapeo a turno response
    public Optional<TurnoResponseDto> buscarPorId(Integer id);

    public List<TurnoResponseDto> buscarTodos();
    public void modificarTurno(TurnoModificarDto turnoModificarDto);

    public void eliminarTurno(Integer id);

}
