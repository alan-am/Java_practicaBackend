package Intro;

import java.util.ArrayList;

public class Grupo {
    private ArrayList<Persona> personas;

    public Grupo(){
        personas  = new ArrayList<>();
    }

    public void agregarPersona(Persona p){
        if(p.ismayorEdad() && p.tieneNombreExtenso() &&  p.chequearNombre() && p.edadValida()){
            personas.add(p);
        }else {
            System.out.println(p.getNombre()+ " no cumple los requisitos para ser agregada/o");
        }
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(ArrayList<Persona> personas) {
        this.personas = personas;
    }



}

