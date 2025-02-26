package com.dh.apirest_clinica.service;

import com.dh.apirest_clinica.entity.Odontologo;

import java.util.Optional;

//Creamos interfaces de los servicios pq nos dan una capa de abstracion,y ademas podemos implementar varias clases de distintos servicios para una entidad
public interface IOdontologoService {

    Optional<Odontologo> buscarPorId(Integer id);

    Odontologo guardar(Odontologo odontologo);

}
