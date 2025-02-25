package com.dh.apirest_clinica;

import com.dh.apirest_clinica.db.H2Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApirestClinicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApirestClinicaApplication.class, args);
		//como nos podemos dar cuenta, en ningun momento instanciamos alguno controlador o servicio.
		H2Connection.initDB();
	}


}
