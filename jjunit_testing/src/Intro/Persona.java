package Intro;

public class Persona {
    private String nombre;
    private  int edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
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

    public boolean ismayorEdad(){
        return  edad >= 18;
    }

    public boolean tieneNombreExtenso(){
        return nombre.length() > 4;
    }

    public boolean chequearNombre(){
        //Chequea q el nombre solo tenga letras de la A - Z
        return nombre.matches("[a-zA-Z]+");

    }

    public boolean edadValida(){
        return edad > 0 && edad < 120;
    }
}
