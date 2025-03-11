package com.dh.apirest_clinica.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean //anotacion que hace q cada que spring escanee los componentes la cargue
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
