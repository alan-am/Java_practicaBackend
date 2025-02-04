package Practica;

public class Circulo extends Figura{
    private double radio;

    public Circulo(double radio) {
        this.radio = radio;
    }

    @Override
    public double calcularArea() {
        if(radio > 0) return Math.PI *(radio*radio);
        System.out.println("El valor del radio debe ser mayor a cero");
        return 0;
    }
}
