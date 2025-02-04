package Practica;

public class Cuadrado extends Figura{
    private double lado;

    public Cuadrado(double lado) {
        this.lado = lado;
    }


    @Override
    public double calcularArea() {
        if(lado > 0 ) return lado * lado;
        System.out.println("El lado del cuadrado debe ser mayor a 0");
        return 0;
    }
}
