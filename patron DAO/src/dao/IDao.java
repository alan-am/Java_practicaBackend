package dao;

import java.util.ArrayList;

public interface IDao <T>{ //T es un dato generico

    T registrar(T t); //recibo un objeto y devuelvo ese mismo objeto con el id
    ArrayList<T> buscarTodos();

    ArrayList<T> buscarPorCampo(String campo);

    T buscarPoId(Integer id);

}
