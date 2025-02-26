package com.dh.apirest_clinica.repository;

import com.dh.apirest_clinica.entity.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//esta es la implementacion del ORM que accedera a la DB con el JPA
//De Jpa sacaremos(override) los metodos necesarios para nuestro CRUD

@Repository
public interface IDomicilioRepository extends JpaRepository<Domicilio, Integer> {

}
