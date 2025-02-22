package test;

import dao.impl.DomicilioDaoH2;
import dao.impl.PacienteDaoH2;
import model.Domicilio;
import model.Paciente;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.PacienteService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PacienteServiceTest {
    private static final Logger log = Logger.getLogger(PacienteServiceTest.class);
    PacienteService pacienteService = new PacienteService(new PacienteDaoH2());

    @BeforeAll
    static void initDB(){
        log.warn("---Inicio de Tests---");
        Connection con = null;
        try {
            Class.forName("org.h2.Driver");
            con  =  DriverManager.getConnection("jdbc:h2:./db_clinica1;INIT=RUNSCRIPT FROM 'initDB.sql'", "root","root");
            log.info("Conexion establecida| H2 |  init DB");
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
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

    @Test
    @DisplayName("Testeo de eliminacion de paciente por id y su domicilio")
    void caso3(){
        //datos
        Integer idPaciente = 1;
        //proceso
        Paciente paciente = pacienteService.borrarPorId(1);
        //validacion
        assertEquals(1, paciente.getId());

    }
}