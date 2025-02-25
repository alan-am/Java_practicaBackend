package com.digitalhouse.mvc_clinica.dao;

import java.util.ArrayList;

public interface IDao <T>{


    T guardar(T t);
    T borrarPorId(Integer id);
    T buscarPorId(Integer id);
    ArrayList<T> buscarTodos();

}
