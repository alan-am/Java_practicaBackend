package Practica;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuadradoTest {

    @Test
    @DisplayName("Validacion del calculo de area de varios cuadrado")
    public void caso1(){
        //datos
        Cuadrado c1 = new Cuadrado(7);
        Cuadrado c2 = new Cuadrado(-2);
        Cuadrado c3 = new Cuadrado(0);

        //proceso
        double area1 = c1.calcularArea();
        double area2 = c2.calcularArea();
        double area3 = c3.calcularArea();

        //assert
        assertTrue(area1 == 49);
        assertTrue(area2 == 0);
        assertTrue(area3 == 0);
    }
}