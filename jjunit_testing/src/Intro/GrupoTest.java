package Intro;

import Intro.Grupo;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GrupoTest {

    //JUnit jupiter

    @BeforeAll
    static public void preTests() {  // debe ser estatico
        System.out.println("Esto se ejecuta antes de ejecutar todos los test");
        //sirve mucho para definir variables utilitarias,
        //definir servicios, etc.
    }
    @BeforeEach
    public void antesDeCadaTest(){
        System.out.println("Esto se ejecuta antes de cada Test");
    }

    @Test
    @DisplayName("Validacion que solo se agregue personas con nombres mayores a 4 letras") //Damos una descripcion al test
    public void grupoCaso1(){
        // Arrange - Act - Assert

        //datos
        Persona p1 = new Persona("Juan", 18);
        Persona p2 = new Persona("Ana", 18);
        Persona p3 = new Persona("Luz", 18);
        Persona p4 = new Persona("Julian", 18);
        Persona p5 = new Persona("Pedro", 18);
        Grupo grupo =new Grupo();

        //proceso
        grupo.agregarPersona(p1);
        grupo.agregarPersona(p2);
        grupo.agregarPersona(p3);
        grupo.agregarPersona(p4);
        grupo.agregarPersona(p5);

        //validacion
        assertEquals(2, grupo.getPersonas().size());
    }

    @Test
    @DisplayName("Validacion que solo se agregue personas mayores a 18")
    public void grupoCaso2(){
        //datos
        Persona p1 = new Persona("Juliann", 18);
        Persona p2 = new Persona("Julian", 17);
        Persona p3 = new Persona("Pedro", 22);
        Persona p4 = new Persona("Pedroo", 14);
        Persona p5 = new Persona("Juliannn", 38);

        Grupo grupo = new Grupo();
        //proceso
        grupo.agregarPersona(p1);
        grupo.agregarPersona(p2);
        grupo.agregarPersona(p3);
        grupo.agregarPersona(p4);
        grupo.agregarPersona(p5);

        //Assert
        assertEquals(3, grupo.getPersonas().size());
     }

    @AfterAll
    static public void postTests() {  // debe ser estatico
        System.out.println("Esto se ejecuta despues de ejecutar todos los test");
    }
    @AfterEach
    public void despuesDeCadaTest(){
        System.out.println("Esto se ejecuta despues de cada Test");
    }

}