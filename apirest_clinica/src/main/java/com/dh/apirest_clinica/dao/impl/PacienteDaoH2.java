package com.dh.apirest_clinica.dao.impl;

import com.dh.apirest_clinica.dao.IDao;
import com.dh.apirest_clinica.model.Paciente;
import com.dh.apirest_clinica.db.H2Connection;
import com.dh.apirest_clinica.model.Domicilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;


@Repository  //Esta anotacion permite que Spring maneje por cuenta propia las instancias que necesite del repositorio
public class PacienteDaoH2 implements IDao<Paciente> {

    private static final Logger log = LoggerFactory.getLogger(PacienteDaoH2.class);
    private DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();
    private static final String INSERT = "INSERT INTO pacientes VALUES(DEFAULT, ?, ?, ?, ?, ?);";
    private static final String SELECT_ID = "SELECT * FROM pacientes WHERE id = ? ";
    private static final String DELETE = "DELETE FROM pacientes WHERE id = ?;";
    private static final String SELECT_ALL = "SELECT * FROM pacientes;";
    private static final String UPDATE = "UPDATE pacientes SET apellido = ?, nombre = ?, dni = ?, fecha_ingreso =  ?, id_domicilio = ? WHERE id = ?;";


    @Override
    public Paciente guardar(Paciente paciente) {
        Connection con = null;
        Paciente pacienteReturn = null;
        Domicilio domicilioPaciente = domicilioDaoH2.guardar(paciente.getDomicilio());
        try{
            con = H2Connection.getConnection();
            log.info("Conexion establecida| H2 |  Guardar paciente");
            con.setAutoCommit(false);
            log.info("Transaccion iniciada..");
            PreparedStatement pStmn  = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            //damos los valores a cada '?'
            pStmn.setString(1, paciente.getApellido());
            pStmn.setString(2, paciente.getNombre());
            pStmn.setString(3, paciente.getDni());
            pStmn.setDate(4, Date.valueOf(paciente.getFechaIngreso())); //Casting
            pStmn.setInt(5, domicilioPaciente.getId());
            int filasAfectadas = pStmn.executeUpdate();
            con.commit();
            log.info("Transaccion finalizada correctamente.., PACIENTE persistido en la DB");
            log.info("Filas Afectadas: "+ filasAfectadas);
            //creamos el objeto desde la db a partir de su id generado por la db
            log.info("Obteniendo el PACIENTE desde la db..");
            Integer id = null;
            ResultSet rs  = pStmn.getGeneratedKeys();
            if(rs.next()){
                id =  rs.getInt(1);
                pacienteReturn = new Paciente(id, paciente.getApellido(), paciente.getNombre(), paciente.getDni(), paciente.getFechaIngreso(), domicilioPaciente);
            }
            log.info("Paciente guardado: " + pacienteReturn);
        }catch(Exception e){
            log.error("Error en la transaccion");
            log.error(e.getMessage());
            e.printStackTrace();

            try {
                con.rollback();
            } catch (SQLException ex) {
                log.error(ex.getMessage());
                ex.printStackTrace();
            }finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                    log.error(ex.getMessage());
                    ex.printStackTrace();
                }
            }

        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return pacienteReturn;
    }

    @Override
    public void borrarPorId(Integer id) {
        Connection con = null;
        Paciente paciente = buscarPorId(id);
            try{
                con = H2Connection.getConnection();
                log.info("Conexion establecida| H2 |  Borrar paciente");
                con.setAutoCommit(false);
                log.info("Transaccion iniciada..");
                Integer filasAfectadas = null;

                if(paciente != null){
                    domicilioDaoH2.borrarPorId(paciente.getDomicilio().getId());
                    PreparedStatement pStmn  = con.prepareStatement(DELETE);
                    //damos los valores a cada '?'
                    pStmn.setInt(1, id);
                    filasAfectadas = pStmn.executeUpdate();
                    //int excp = 7/0;
                    con.commit();
                }

                log.info("Transaccion finalizada correctamente.., PACIENTE ELIMINADO en la DB");
                log.info("Filas Afectadas: "+ filasAfectadas);
            }catch(Exception e){
                log.error("Error en la transaccion");
                log.error(e.getMessage());
                e.printStackTrace();

                try {
                    con.rollback();
                } catch (SQLException ex) {
                    log.error(ex.getMessage());
                    ex.printStackTrace();
                }finally {
                    try {
                        con.setAutoCommit(true);
                    } catch (SQLException ex) {
                        log.error(ex.getMessage());
                        ex.printStackTrace();
                    }
                }

            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                }
            }
    }

    @Override
    public Paciente buscarPorId(Integer id) {
        Paciente pacienteReturn = null;
        try(Connection con = H2Connection.getConnection()){
            log.info("Conexion establecida| H2 |  buscar Paciente por ID");
            PreparedStatement pStmn  = con.prepareStatement(SELECT_ID);
            pStmn.setInt(1, id);
            ResultSet rs = pStmn.executeQuery();
            log.info("Consulta ejecutada..");
            if(rs.next()){
                pacienteReturn = new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5).toLocalDate(), domicilioDaoH2.buscarPorId(rs.getInt(6)));
                log.info("Paciente Encontrado "+ pacienteReturn);
            }
            if(pacienteReturn == null) log.info("No se han encontrado coincidencias");
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return pacienteReturn;
    }

    @Override
    public ArrayList<Paciente> buscarTodos() {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        try(Connection con = H2Connection.getConnection()){
            log.info("Conexion establecida| H2 |  buscar todos los pacientes");
            Statement stmn = con.createStatement();
            ResultSet rs = stmn.executeQuery(SELECT_ALL);
            log.info("Consulta ejecutada..");
            int i = 0;
            while (rs.next()){
                pacientes.add(new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5).toLocalDate(), domicilioDaoH2.buscarPorId(rs.getInt(6))));
                i++;
            }
            log.info("Pacientes encontrados: "+ i);
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return pacientes;
    }

    @Override
    public void modificar(Paciente paciente){
        Connection con = null;
        domicilioDaoH2.modificar(paciente.getDomicilio());
        try{
            con = H2Connection.getConnection();
            log.info("Conexion establecida| H2 |  Modificar Paciente");
            con.setAutoCommit(false);
            log.info("Transaccion iniciada..");
            PreparedStatement pStmn  = con.prepareStatement(UPDATE);
            //damos los valores a cada '?'
            pStmn.setString(1, paciente.getApellido());
            pStmn.setString(2, paciente.getNombre());
            pStmn.setString(3, paciente.getDni());
            pStmn.setDate(4, Date.valueOf(paciente.getFechaIngreso())); //Casting
            pStmn.setInt(5, paciente.getDomicilio().getId());
            pStmn.setInt(6, paciente.getId());
            int filasAfectadas = pStmn.executeUpdate();
            con.commit();
            log.info("Transaccion finalizada correctamente.., PACIENTE modificado en la DB");
            log.info("Filas Afectadas: "+ filasAfectadas);
        }catch(Exception e){
            log.error("Error en la transaccion");
            log.error(e.getMessage());
            e.printStackTrace();

            try {
                con.rollback();
            } catch (SQLException ex) {
                log.error(ex.getMessage());
                ex.printStackTrace();
            }finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                    log.error(ex.getMessage());
                    ex.printStackTrace();
                }
            }

        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }

    }




}
