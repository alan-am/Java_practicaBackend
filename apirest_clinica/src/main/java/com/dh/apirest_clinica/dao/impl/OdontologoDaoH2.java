package com.dh.apirest_clinica.dao.impl;

import com.dh.apirest_clinica.dao.IDao;
import com.dh.apirest_clinica.db.H2Connection;
import com.dh.apirest_clinica.model.Odontologo;
import com.dh.apirest_clinica.model.Odontologo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class OdontologoDaoH2 implements IDao<Odontologo> {
    private static final Logger log = LoggerFactory.getLogger(OdontologoDaoH2.class);
    private static final String INSERT = "INSERT INTO odontologos VALUES(DEFAULT, ?, ?, ?);";
    private static final String SELECT_ID = "SELECT * FROM odontologos where id = ?";

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Odontologo odontologoARetornar =  null;
        Connection con = null;
        try{
            con = H2Connection.getConnection();
            log.info("Conexion establecida| H2 |  Guardar Odontologo");
            con.setAutoCommit(false);
            PreparedStatement sentenciaPreparada = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            sentenciaPreparada.setString(1, odontologo.getMatricula());
            sentenciaPreparada.setString(2, odontologo.getApellido());
            sentenciaPreparada.setString(3, odontologo.getNombre());
            int filasAfectadas = sentenciaPreparada.executeUpdate();
            con.commit();
            log.info("Transaccion finalizada correctamente.., ODONTOLOGO persistido en la DB");
            log.info("Filas Afectadas: "+ filasAfectadas);
            //Devolver datos
            log.info("Obteniendo el Odontologo desde la db..");
            Integer id = null;
            ResultSet rs  = sentenciaPreparada.getGeneratedKeys();
            if(rs.next()){
                id =  rs.getInt(1);
                odontologoARetornar = new Odontologo(id, odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());
            }
            log.info("Odontologo guardado: " + odontologoARetornar);
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            try {
                con.rollback();
                log.error("Hubo un problema, se hizo un rollback a la transaccion");
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
        return odontologoARetornar;
    }

    @Override
    public void borrarPorId(Integer id) {

    }

    @Override
    public Odontologo buscarPorId(Integer id) {
        Odontologo odontologo = null;
        try(Connection con = H2Connection.getConnection()){
            log.info("Conexion establecida| H2 |  buscar Odontologo por ID");
            PreparedStatement pStmn  = con.prepareStatement(SELECT_ID);
            pStmn.setInt(1, id);
            ResultSet rs = pStmn.executeQuery();
            log.info("Consulta ejecutada..");
            if(rs.next()){
                odontologo = new Odontologo(id, rs.getString(2), rs.getString(3), rs.getString(4));
                log.info("Odontologo Encontrado "+ odontologo);
            }
            if(odontologo == null) log.info("No se han encontrado coincidencias");
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return odontologo;
    }

    @Override
    public void modificar(Odontologo odontologo) {

    }

    @Override
    public ArrayList<Odontologo> buscarTodos() {
        return null;
    }
}
