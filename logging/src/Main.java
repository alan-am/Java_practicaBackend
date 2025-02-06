import org.apache.log4j.Logger;

import java.util.Scanner;

public class Main {
    //Instanciamos el Log de manera estatica para poder utilizarlo en toda la clase
    public static final Logger log = Logger.getLogger(Main.class);
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("======Registro de personas========");
        log.info("Iniciando sistema...");
        String eleccion = "n";
        do {
            log.info("Se procedera a registrar una nueva persona");
            System.out.print("Ingrese el nombre: ");
            String nombre = sc.nextLine();
            int edad = 0;
            //validacion
            try{
                System.out.print("Ingrese edad: ");
                edad = Integer.parseInt(sc.nextLine());
                if(edad > 120 || edad < 0 ) throw new EdadInvalida();
                Persona p = new Persona(nombre, edad);
            }catch(EdadInvalida e){
                log.error(e.getMessage());
            }catch (Exception e ){
                log.error("Error desconocido");
            }
            System.out.print("Desea continuar?(S/N): ");
            eleccion = sc.nextLine();
            System.out.println(); // /n
        }while (eleccion.equalsIgnoreCase("s"));
        sc.close();
        log.info("Cerrando sistema...");
    }
}
