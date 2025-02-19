import org.apache.log4j.Logger;

import java.sql.*;

public class Main {
    static final Logger log = Logger.getLogger(Main.class);

    //sentencias SQLa utilizar
    static final String CREATE = "DROP TABLE animales IF EXISTS;" +
            "CREATE TABLE animales( id int PRIMARY KEY AUTO_INCREMENT," +
            "nombre varchar(45) NOT NULL, " +
            "tipo varchar(15) NOT NULL);";
    static final String INSERT = "INSERT INTO animales(nombre, tipo)" +
            "VALUES" +
            "('Firulais', 'Perro')," +  //varchar siempre en comillas simples.
            "('Garfield', 'Gato')," +
            "('Zeus','Perro')," +
            "('Goat', 'Cabra')," +
            "('Winnie', 'Oso');";
    static final String SELECT = "Select * from animales;";
    static final String DELETE = "DELETE FROM animales WHERE id = 4 ";

    public static void main(String[] args) {


        log.info("Iniciando sistema...");
        //primero revisar getConnection

        try(Connection connection = getConnection()) { //realizamos la conexion

            Statement stmn = connection.createStatement(); //creo una estructura 'statement' q me permitira mandar sentencias SQL

            //ejecuto las sentencias
            stmn.execute(CREATE);
            stmn.execute(INSERT);
            ResultSet rs = stmn.executeQuery(SELECT); //devuelve la tabla del select

            log.info("Devolviendo los datos en la DB..");
            log.info("ID |  Nombre   |  Tipo ");
            while(rs.next()){
                log.info(rs.getInt(1)+" | "+rs.getString(2)+" | "+ rs.getString(3));
            }

            log.info("se procedera a eliminar el registro con ID =  4");
            stmn.execute(DELETE);

            log.info("Volvemos a imprimar la tabla de la base..");
            rs = stmn.executeQuery(SELECT);
            while(rs.next()){
                log.info(rs.getInt(1)+" | "+rs.getString(2)+" | "+ rs.getString(3));
            }

        }catch(Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }


    //* Metodo que devuelve una conexion
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver"); //Carga dinamicamente toda la clase necesaria para la conexion a H2
        return DriverManager.getConnection("jdbc:h2:./dbtest1", "root","root"); //definimos el lugar de guardado de h2, usuario y password
    }


}
