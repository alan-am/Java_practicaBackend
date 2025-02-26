package com.dh.apirest_clinica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //Aqui configuremos el Cors y demas configuracion de seguridad a lo largo del proyecto(login,etc)

    public void addCorsMappings(CorsRegistry registry){ //metodo de configuracion del CORS
        registry.addMapping("/**") //todos los endpoints del backend  pueden recibir peticiones
                .allowedOriginPatterns("*") //aqui la url del front dominio.com, en este caso esta expuesto para desde cualquier url
                .allowedMethods("GET", "POST", "PUT","DELETE") //metodos permitidos
                .allowedHeaders("*") //acepta cualquier tipo de header
                .allowCredentials(true); //acepta cualquier tipo de credencial
    }
}
