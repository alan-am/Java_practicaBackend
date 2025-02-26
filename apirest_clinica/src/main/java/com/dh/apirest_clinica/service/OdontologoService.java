package com.dh.apirest_clinica.service;

import com.dh.apirest_clinica.dao.IDao;
import com.dh.apirest_clinica.dao.impl.OdontologoDaoH2;
import com.dh.apirest_clinica.model.Odontologo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OdontologoService {
    private IDao<Odontologo> odontologoIDao;
    private static final Logger log = LoggerFactory.getLogger(OdontologoService.class);

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    public Odontologo buscarPorId(Integer id){
        return odontologoIDao.buscarPorId(id);
    }

    public Odontologo guardar(Odontologo odontologo){
        return odontologoIDao.guardar(odontologo);
    }

}
