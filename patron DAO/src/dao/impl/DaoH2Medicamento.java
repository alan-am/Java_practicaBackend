package dao.impl;

// la carpeta impl , implementa el dao para cada db

import dao.IDao;
import db.H2Connection;
import model.Medicamento;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class DaoH2Medicamento implements IDao<Medicamento> { // se crea una implementacion por cada entidad

    private static final Logger log = Logger.getLogger(DaoH2Medicamento.class);
    private static final String REGISTRO = "INSERT INTO medicamentos(codigo, nombre, laboratorio, cantidad, precio) VALUES" +
            "(?,?,?,?,?)";
    private static final String SELECT_NOMBRE = "SELECT * FROM  medicamentos WHERE nombre = ?;";

    private static final String SELECT_ID = "SELECT * FROM medicamentos WHERE id = ?;";
    private static final String SELECT_ALL = "SELECT * FROM medicamentos;";

    @Override
    public Medicamento registrar(Medicamento medicamento) {
        Connection con = null;
        Medicamento medicamentoARetornar = null;
        try{
            con = H2Connection.getConnection();
            log.info("Conexion establecida| H2 |  registrar Medicamento");
            con.setAutoCommit(false);
            log.info("Transaccion iniciada..");
            PreparedStatement pStmn  = con.prepareStatement(REGISTRO, Statement.RETURN_GENERATED_KEYS);
            //damos los valores a cada '?'
            pStmn.setInt(1, medicamento.getCodigo());
            pStmn.setString(2, medicamento.getNombre());
            pStmn.setString(3, medicamento.getLaboratorio());
            pStmn.setInt(4, medicamento.getCantidad());
            pStmn.setDouble(5, medicamento.getPrecio());
            int filasAfectadas = pStmn.executeUpdate(); //ejecutamos la sentencia sql ya armada
            con.commit();
            log.info("Transaccion finalizada correctamente.., medicamento persistido en la DB");
            log.info("Filas Afectadas: "+ filasAfectadas);
            //creamos el objeto desde la db a partir de su id generado por la db
            log.info("Obteniendo el medicamento desde la db..");
            Integer id = null;
            ResultSet rs  = pStmn.getGeneratedKeys(); // devuelve un resultSet de las claves generadas en el insert , en este caso solo seria 1
            if(rs.next()){
                id =  rs.getInt(1);
            }
            medicamentoARetornar = new Medicamento(id,medicamento.getCodigo(), medicamento.getNombre(), medicamento.getLaboratorio(), medicamento.getCantidad(), medicamento.getPrecio());
            log.info(medicamentoARetornar);
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
        return medicamentoARetornar;
    }

    @Override
    public ArrayList<Medicamento> buscarTodos() {
        ArrayList<Medicamento> medicamentos =  new ArrayList<>();
        try(Connection con = H2Connection.getConnection()){
            log.info("Conexion establecida| H2 |  buscar todos los medicamentos");
            Statement stmn  = con.createStatement();
            ResultSet rs = stmn.executeQuery(SELECT_ALL);
            log.info("consulta ejecutada.. devolviendo datos..");
            while(rs.next()){
                medicamentos.add(new Medicamento(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDouble(6)));
            }
            log.info(medicamentos);
            if(medicamentos.size() == 0) log.info("No existen medicamentos en la db");

        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return medicamentos;
    }

    @Override
    public ArrayList<Medicamento> buscarPorCampo(String campo) {
        Medicamento medicamentoEncontrado = null;
        ArrayList<Medicamento> medicamentos =  new ArrayList<>();
        try(Connection con = H2Connection.getConnection()){
            log.info("Conexion establecida| H2 |  buscar por nombre Medicamento/s");
            PreparedStatement pStmn  = con.prepareStatement(SELECT_NOMBRE);
            pStmn.setString(1, campo);
            ResultSet rs = pStmn.executeQuery();
            while(rs.next()){
                medicamentoEncontrado = new Medicamento(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDouble(6));
                log.info("Medicamento coincidente: "+medicamentoEncontrado);
                medicamentos.add(medicamentoEncontrado);
            }
            if(medicamentos.size() == 0) log.info("No se han encontrado coincidencias");

        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return medicamentos;
    }

    @Override
    public Medicamento buscarPoId(Integer id) {
        Medicamento medicamentoARetornar = null;
        try(Connection con = H2Connection.getConnection()){
            log.info("Conexion establecida| H2 |  buscar por Medicamento por ID");
            PreparedStatement pStmn  = con.prepareStatement(SELECT_ID);
            pStmn.setInt(1, id);
            ResultSet rs = pStmn.executeQuery();
            log.info("Consulta ejecutada..");
            if(rs.next()){
                medicamentoARetornar = new Medicamento(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDouble(6));
                log.info(medicamentoARetornar);
            }
            if(medicamentoARetornar == null) log.info("No se han encontrado coincidencias");
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return medicamentoARetornar;
    }


}
