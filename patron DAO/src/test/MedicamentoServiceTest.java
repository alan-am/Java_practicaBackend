package test;

import dao.impl.DaoH2Medicamento;
import db.H2Connection;
import model.Medicamento;
import org.apache.log4j.Logger;
import org.h2.Driver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.MedicamentoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MedicamentoServiceTest {
    private  static final Logger log = Logger.getLogger(MedicamentoServiceTest.class);
    MedicamentoService medicamentoService = new MedicamentoService(new DaoH2Medicamento()); //aqui en la definicio del service pasamos el Dao(estrategia) de la base de datos que queremos y la entidad, podria ser DaoMySQLMedicamento

    @BeforeAll
    static void initDB(){
        log.warn("---Inicio de Tests---");
        Connection con = null;
        try{
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:./db_farmaceutica1;INIT=RUNSCRIPT FROM 'initDB.sql'",  "root","root");
            log.info("Conexion establecida| H2 |  init DB");

        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Test
    @DisplayName("Guardado de medicamento en la DB H2")
    void caso1(){
        //datos
        Medicamento m = new Medicamento(2401, "Ibuprofeno", "FarmaLabs", 67, 0.376);
        //proceso
        Medicamento medicamentoDesdeDb = medicamentoService.guardarMedicamento(m);
        //validacion
        assertNotNull(medicamentoDesdeDb.getId());

    }

    @Test
    @DisplayName("Busqueda de medicamento por su ID")
    void caso2(){
        //datos-proceso
        Medicamento m = medicamentoService.buscarPorId(1);
        //validacion
        assertNotNull(m.getId());
    }

    @Test
    @DisplayName("Busqueda de medicamento/s por nombre")
    void caso3(){
        //datos
        String nombre = "Ibuprofeno"; //tener en cuenta Mayus y Min
        //proceso
        ArrayList<Medicamento> medicamentos = medicamentoService.buscarPorCampo(nombre);
        //validacion
        assertEquals(3, medicamentos.size());

    }

    @Test
    @DisplayName("Busqueda de todos los medicamentos")
    void caso4(){
        //datos-proceso
        ArrayList<Medicamento> medicamentos = medicamentoService.buscarTodos();
        //validacion
        assertEquals(4, medicamentos.size());
    }
}