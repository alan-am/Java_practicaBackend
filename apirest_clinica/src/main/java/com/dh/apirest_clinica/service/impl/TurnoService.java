package com.dh.apirest_clinica.service.impl;

import com.dh.apirest_clinica.dto.request.TurnoModificarDto;
import com.dh.apirest_clinica.dto.request.TurnoRequestDto;
import com.dh.apirest_clinica.dto.response.OdontologoResponseDto;
import com.dh.apirest_clinica.dto.response.PacienteResponseDto;
import com.dh.apirest_clinica.dto.response.TurnoResponseDto;
import com.dh.apirest_clinica.entity.Odontologo;
import com.dh.apirest_clinica.entity.Paciente;
import com.dh.apirest_clinica.entity.Turno;
import com.dh.apirest_clinica.exception.ResourceNotFoundException;
import com.dh.apirest_clinica.repository.ITurnoRepository;
import com.dh.apirest_clinica.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class  TurnoService implements ITurnoService {
    private static final Logger log = LoggerFactory.getLogger(TurnoService.class);
    private PacienteService pacienteService;
    private OdontologoService odontologoService;
    private ITurnoRepository iTurnoRepository;
    @Autowired //otra forma de hacer inyeccion de dependencia sin desarmar el constructor
    private ModelMapper modelMapper;

    public TurnoService(PacienteService pacienteService, OdontologoService odontologoService, ITurnoRepository iTurnoRepository) {
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.iTurnoRepository = iTurnoRepository;
    }

    //funcion para mapear un turno a una turnoResponse
    private TurnoResponseDto mapeoATurnoResponse(Turno turno){
        //mapeo inverso
        //para este caso turno response tiene Paciente y Odontologo response
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto(turno.getOdontologo().getId(), turno.getOdontologo().getMatricula(),turno.getOdontologo().getNombre(), turno.getOdontologo().getApellido());
        PacienteResponseDto pacienteResponseDto = new PacienteResponseDto(turno.getPaciente().getId(), turno.getPaciente().getNombre(), turno.getPaciente().getApellido(), turno.getPaciente().getDni());
        TurnoResponseDto turnoResponseDto = new TurnoResponseDto(turno.getId(), pacienteResponseDto, odontologoResponseDto, turno.getFecha().toString());
        log.info("Response de Turno generado");
        return turnoResponseDto;
    }


    @Override
    public TurnoResponseDto guardar(TurnoRequestDto turnoRequestDto){ // el turno viene solo con el id de paciente y odontologo
        log.info("Ingresando al service de Turno");
        Optional<Paciente> paciente = pacienteService.buscarPorID(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());
        Turno turno = new Turno(); //turno a armar(a partir del turno request) para persistir en la db
        Turno turnoDesdeDB = null;
        TurnoResponseDto turnoResponseDto = null;
        if (paciente.isPresent() && odontologo.isPresent() ) {
            log.info("Paciente y odontologos encontrados en la db");
            //mapeo del turno-request a turno
            turno.setPaciente(paciente.get()); //.get devuelve el tipo de dato dentro del opcional
            turno.setOdontologo(odontologo.get());
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            //persistencia del turno
            turnoDesdeDB = iTurnoRepository.save(turno);
            log.info("Turno persistido en la db");

            //mapeo inverso
            turnoResponseDto = mapeoATurnoResponse(turnoDesdeDB);
        }
        return turnoResponseDto;
    }


    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id){ //podriamos cambiar la firma para retorne un turno y ya no un optional, pero para no complicarnos wrapeamos en un opcional
        log.info("Ingresando al Service de Turno| Buscar por id");
        Optional<Turno> turnoDesdeDB = iTurnoRepository.findById(id);
        TurnoResponseDto turnoResponseDto = null;
        if(turnoDesdeDB.isPresent()){
            //mapeamos
            turnoResponseDto = mapeoATurnoResponse(turnoDesdeDB.get());
        }
        return Optional.ofNullable(turnoResponseDto);
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        log.info("Ingresando al Service de Turno| Buscar Todos");
        List<Turno> turnos = iTurnoRepository.findAll();
        List<TurnoResponseDto> turnosResponse = new ArrayList<>();
        for(Turno turno: turnos){
            turnosResponse.add(mapeoATurnoResponse(turno));
        }
        return turnosResponse;
    }

    @Override
    public void modificarTurno(TurnoModificarDto turnoModificarDto) { //OJO aqui es necesario crear otro request de turno q vengo con ID
        log.info("Ingresando al Service de Turno| Modificar Turno");
        Optional<Paciente> paciente = pacienteService.buscarPorID(turnoModificarDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoModificarDto.getOdontologo_id());
        Turno turno = null;
        if (paciente.isPresent() && odontologo.isPresent() ) {
            //mapeo de turnoModificar a turno
            turno = new Turno(turnoModificarDto.getId(), paciente.get(), odontologo.get(), LocalDate.parse(turnoModificarDto.getFecha()));
            iTurnoRepository.save(turno); //persistimos el turno
            log.info("Turno persistido en la DB");
        }
    }

    @Override
    public void eliminarTurno(Integer id) {
        Optional<Turno> turnoEncontrado = iTurnoRepository.findById(id);
        if(turnoEncontrado.isEmpty()){
            throw new ResourceNotFoundException("El turno a eliminar no fue encontrado");
        }
        iTurnoRepository.deleteById(id);
    }

    //uso de modelMapper en vez de la funcion hecha a mano (no implementado)
    private TurnoResponseDto mapperATurnoResponse(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return turnoResponseDto;
    }
}
