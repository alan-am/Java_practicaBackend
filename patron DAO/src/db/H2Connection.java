package db;

import org.apache.log4j.Logger;
import org.h2.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//en el paquete db, manejamos las conexiones a las distintas bases
public class H2Connection {

    private static final Logger log = Logger.getLogger(H2Connection.class);
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:./db_farmaceutica1", "root","root");
    }
}
