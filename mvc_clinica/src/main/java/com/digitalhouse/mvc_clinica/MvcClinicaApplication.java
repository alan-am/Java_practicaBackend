package com.digitalhouse.mvc_clinica;

import com.digitalhouse.mvc_clinica.db.H2Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// to do lo relacionado a este Punto de partida tiene que esta en la carpeta del dominio

@SpringBootApplication
public class MvcClinicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcClinicaApplication.class, args);
		//como nos podemos dar cuenta, en ningun momento instanciamos alguno controlador o servicio.
		H2Connection.initDB();
	}



}
