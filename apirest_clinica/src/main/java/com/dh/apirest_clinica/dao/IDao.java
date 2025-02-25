package com.dh.apirest_clinica.dao;

import com.dh.apirest_clinica.model.Domicilio;

import java.util.ArrayList;

public interface IDao <T>{


    T guardar(T t);
    void borrarPorId(Integer id);
    T buscarPorId(Integer id);
    void modificar(T t); //por convencion eliminar y modificar son void
    ArrayList<T> buscarTodos();


}
