import org.apache.log4j.Logger;

public class Persona {
    private static  Logger log = Logger.getLogger(Persona.class);
    private String nombre;
    private int edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        log.info("Se ha creado a la persona -> "+this.toString());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString(){
        return nombre +" ,"+edad+ " anios";
    }
}

