package com.dh.apirest_clinica.dao.impl;

import com.dh.apirest_clinica.dao.IDao;
import com.dh.apirest_clinica.db.H2Connection;
import com.dh.apirest_clinica.model.Domicilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class DomicilioDaoH2 implements IDao<Domicilio> {
    private static final Logger log = LoggerFactory.getLogger(DomicilioDaoH2.class);
    private static String INSERT = "INSERT INTO domicilios VALUES(DEFAULT, ?, ?, ?, ?);";
    private static final String SELECT_ID = "SELECT * FROM domicilios WHERE id = ?;";
    private static final String DELETE = "DELETE FROM domicilios WHERE id = ?;";

    private  static final String UPDATE = "UPDATE domicilios SET calle = ?, numero =?, localidad = ?, provincia = ? WHERE id = ?;";
    @Override
    public Domicilio guardar(Domicilio domicilio) {
        Connection con = null;
        Domicilio domicilioReturn = null;
        try{
            con = H2Connection.getConnection();
            log.info("Conexion establecida| H2 |  guardar Domicilio");
            con.setAutoCommit(false);
            log.info("Transaccion iniciada..");
            //damos los valores a cada '?'
            if(domicilio.getId() != null) {//si el domicilio no tiene nulo en ID, significa que es una restauracion de datos
                INSERT = "INSERT INTO domicilios VALUES(?, ?, ?, ?, ?);";
            }
            PreparedStatement pStmn  = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            if(domicilio.getId() != null){
                pStmn.setInt(1, domicilio.getId());
                pStmn.setString(2, domicilio.getCalle());
                pStmn.setInt(3, domicilio.getNumero());
                pStmn.setString(4, domicilio.getLocalidad());
                pStmn.setString(5, domicilio.getProvincia());
            }else{
                pStmn.setString(1, domicilio.getCalle());
                pStmn.setInt(2, domicilio.getNumero());
                pStmn.setString(3, domicilio.getLocalidad());
                pStmn.setString(4, domicilio.getProvincia());
            }
            int filasAfectadas = pStmn.executeUpdate();
            con.commit();
            log.info("Transaccion finalizada correctamente.., DOMICILIO persistido en la DB");
            log.info("Filas Afectadas: "+ filasAfectadas);
            //creamos el objeto desde la db a partir de su id generado por la db
            log.info("Obteniendo el DOMICILIO desde la db..");
            Integer id = null;
            if(domicilio.getId() == null){
                ResultSet rs  = pStmn.getGeneratedKeys();
                if(rs.next()){
                    id =  rs.getInt(1);
                    domicilioReturn = new Domicilio(id, domicilio.getCalle(), domicilio.getNumero(), domicilio.getLocalidad(), domicilio.getProvincia());
                }
            }else {
                domicilioReturn = new Domicilio(domicilio.getId(), domicilio.getCalle(), domicilio.getNumero(), domicilio.getLocalidad(), domicilio.getProvincia());
            }
            log.info("Domicilio guardado: " + domicilioReturn);
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
        return domicilioReturn;
    }


    @Override
    public void borrarPorId(Integer id) {
        Connection con = null;
        try{
            con = H2Connection.getConnection();
            log.info("Conexion establecida| H2 |  Borrar Domicilio");
            con.setAutoCommit(false);
            log.info("Transaccion iniciada..");
            PreparedStatement pStmn  = con.prepareStatement(DELETE);
            //damos los valores a cada '?'
            pStmn.setInt(1, id);
            int filasAfectadas = pStmn.executeUpdate();
            //int excp = 7/0;
            con.commit();
            log.info("Primera parte de la transaccion Completada.., DOMICILIO borrado de la DB");
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
    public Domicilio buscarPorId(Integer id) {
        Domicilio domicilioReturn = null;
        try(Connection con = H2Connection.getConnection()){
            log.info("Conexion establecida| H2 |  buscar por Domicilio por ID");
            PreparedStatement pStmn  = con.prepareStatement(SELECT_ID);
            pStmn.setInt(1, id);
            ResultSet rs = pStmn.executeQuery();
            log.info("Consulta ejecutada..");
            if(rs.next()){
                domicilioReturn = new Domicilio(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                log.info("Domicilio encontrado: " + domicilioReturn);
            }
            if(domicilioReturn == null) log.info("No se han encontrado coincidencias");
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return domicilioReturn;
    }

    @Override
    public void modificar(Domicilio domicilio) {

        Connection con = null;
        try{
            con = H2Connection.getConnection();
            log.info("Conexion establecida| H2 |  Modificar Domicilio");
            con.setAutoCommit(false);
            log.info("Transaccion iniciada..");
            //damos los valores a cada '?'
            PreparedStatement pStmn  = con.prepareStatement(UPDATE);
                pStmn.setString(1, domicilio.getCalle());
                pStmn.setInt(2, domicilio.getNumero());
                pStmn.setString(3, domicilio.getLocalidad());
                pStmn.setString(4, domicilio.getProvincia());
                pStmn.setInt(5,domicilio.getId());
            int filasAfectadas = pStmn.executeUpdate();
            con.commit();
            log.info("Transaccion finalizada correctamente.., DOMICILIO modificado en la DB");
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
    public ArrayList<Domicilio> buscarTodos() {
        return null;
    }

}
