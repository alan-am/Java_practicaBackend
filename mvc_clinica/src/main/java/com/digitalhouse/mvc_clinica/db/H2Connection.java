package com.digitalhouse.mvc_clinica.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Connection {
    private static final Logger log = LoggerFactory.getLogger(H2Connection.class);

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:./db_clinica2", "root","root");
    }

    public static void initDB(){
        Connection con = null;
        try {
            Class.forName("org.h2.Driver");
            con  =  DriverManager.getConnection("jdbc:h2:./db_clinica2;INIT=RUNSCRIPT FROM 'initDB.sql'", "root","root");
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
}
