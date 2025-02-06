public class EdadInvalida extends RuntimeException{
    public EdadInvalida() {
        super("Se ha ingresado una edad invalida, la persona no ha sido registrada.");
    }
}
