package dao.impl;

import dao.IDao;
import db.H2Connection;
import model.Domicilio;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class DomicilioDaoH2 implements IDao<Domicilio> {
    private static final Logger log = Logger.getLogger(DomicilioDaoH2.class);
    private static String INSERT = "INSERT INTO domicilios VALUES(DEFAULT, ?, ?, ?, ?);";
    private static final String SELECT_ID = "SELECT * FROM domicilios WHERE id = ?;";
    private static final String DELETE = "DELETE FROM domicilios WHERE id = ?;";
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
    public Domicilio borrarPorId(Integer id) {
        Connection con = null;
        Domicilio domicilio = null; //buscamos el domicilio a borrar
        try{
            con = H2Connection.getConnection();
            log.info("Conexion establecida| H2 |  Borrar Domicilio");
            con.setAutoCommit(false);
            log.info("Transaccion iniciada..");
            PreparedStatement pStmn  = con.prepareStatement(DELETE);
            //damos los valores a cada '?'
            pStmn.setInt(1, id);
            int filasAfectadas = pStmn.executeUpdate();
            domicilio = this.buscarPorId(id); //crea otra conexion asiq en teoria puede leer aun el registro yaq no se ha asentado el commit
            //int excp = 7/0;
            con.commit();
            log.info("Primera parte de la transaccion Completada.., DOMICILIO borrado en la DB");
            log.info("Filas Afectadas: "+ filasAfectadas);
            //creamos el objeto desde la db a partir de su id generado por la db
            log.info("Domicilio borrado: " + domicilio);
        }catch(Exception e){
            log.error("Error en la transaccion");
            log.error(e.getMessage());
            e.printStackTrace();
            try {
                con.rollback();
                domicilio = null;
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
        return domicilio;
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
    public ArrayList<Domicilio> buscarTodos() {
        return null;
    }

}
