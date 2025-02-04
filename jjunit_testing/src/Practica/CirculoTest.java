package Practica;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CirculoTest {

        @Test
        @DisplayName("Validacion del calculo de area de varios circulos")
        public void caso1(){
            //datos
            Circulo c1 = new Circulo(1);
            Circulo c2 = new Circulo(-2.05);
            Circulo c3 = new Circulo(0);

            //proceso
            double area1 = c1.calcularArea();
            double area2 = c2.calcularArea();
            double area3 = c3.calcularArea();

            //assert
            assertTrue(area1 == Math.PI);
            assertTrue(area2 == 0);
            assertTrue(area3 == 0);
        }

}