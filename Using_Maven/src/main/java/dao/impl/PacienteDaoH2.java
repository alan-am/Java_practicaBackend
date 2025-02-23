package dao.impl;

import dao.IDao;
import db.H2Connection;
import model.Domicilio;
import model.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class PacienteDaoH2 implements IDao<Paciente> {

    private static final Logger log = Logger.getLogger(PacienteDaoH2.class);
    private DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();
    private static final String INSERT = "INSERT INTO pacientes VALUES(DEFAULT, ?, ?, ?, ?, ?);";
    private static final String SELECT_ID = "SELECT * FROM pacientes WHERE id = ? ";
    private static final String DELETE = "DELETE FROM pacientes WHERE id = ?;";

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
    public Paciente borrarPorId(Integer id) {
        Connection con = null;
        Paciente paciente = this.buscarPorId(id);
        Domicilio domicilioPaciente = domicilioDaoH2.borrarPorId(paciente.getDomicilio().getId());
        if(domicilioPaciente != null){
            try{
                con = H2Connection.getConnection();
                log.info("Conexion establecida| H2 |  Borrar paciente");
                con.setAutoCommit(false);
                log.info("Transaccion iniciada..");
                PreparedStatement pStmn  = con.prepareStatement(DELETE);
                //damos los valores a cada '?'
                pStmn.setInt(1, paciente.getId());
                int filasAfectadas = pStmn.executeUpdate();
                //int excp = 7/0;
                con.commit();
                log.info("Transaccion finalizada correctamente.., PACIENTE ELIMINADO en la DB");
                log.info("Filas Afectadas: "+ filasAfectadas);
                //creamos el objeto desde la db a partir de su id generado por la db

                log.info("Paciente Eliminado: " + paciente);
            }catch(Exception e){
                log.error("Error en la transaccion");
                log.error(e.getMessage());
                e.printStackTrace();

                try {
                    //si sucede algo hay que devolver el domicilio eliminado
                    domicilioDaoH2.guardar(domicilioPaciente);
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
        return paciente;
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
        return null;
    }
}
