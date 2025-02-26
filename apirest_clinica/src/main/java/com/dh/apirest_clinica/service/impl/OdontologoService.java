package com.dh.apirest_clinica.service.impl;

import com.dh.apirest_clinica.entity.Odontologo;
import com.dh.apirest_clinica.repository.IOdontologoRepository;
import com.dh.apirest_clinica.service.IOdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    private IOdontologoRepository iOdontologoRepository;
    private static final Logger log = LoggerFactory.getLogger(OdontologoService.class);


    @Override
    public Optional<Odontologo> buscarPorId(Integer id) {
        return iOdontologoRepository.findById(id);
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        return iOdontologoRepository.save(odontologo);
    }
}
