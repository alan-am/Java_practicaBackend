package com.dh.apirest_clinica;

import com.dh.apirest_clinica.dao.impl.PacienteDaoH2;
import com.dh.apirest_clinica.db.H2Connection;
import com.dh.apirest_clinica.model.Domicilio;
import com.dh.apirest_clinica.model.Paciente;
import com.dh.apirest_clinica.service.PacienteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PacienteServiceTest {
    private static final Logger log = LoggerFactory.getLogger(PacienteServiceTest.class);
    PacienteService pacienteService = new PacienteService(new PacienteDaoH2());

    @BeforeAll
    static void initDB(){
        log.warn("---Inicio de Tests---");
        H2Connection.initDB();
    }

    @Test
    @DisplayName("Testeo de Guardado de pacientes con su domicilio")
    void caso1(){
        //datos
        Domicilio domicilio = new Domicilio("Av. desconocida", 31, "Kennedy", "Guayas");
        Paciente paciente = new Paciente("Valdiviezo", "Janet", "0912345678", LocalDate.of(2024, 12, 31), domicilio);
        //proceso
        Paciente p = pacienteService.guardarPaciente(paciente);
        //validacion
        assertNotNull(p);
    }

    @Test
    @DisplayName("Testeo de busqueda de paciente por id y su domicilio")
    void caso2(){
        //datos
        Integer id = 4;
        // proceso
        Paciente paciente = pacienteService.buscarPorID(id);
        //validacion
        assertEquals(4, paciente.getDomicilio().getId());
    }

}