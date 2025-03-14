package com.digitalhouse.hi_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
@RequestMapping("/backend")
public class HiSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiSpringbootApplication.class, args);
	}


	@GetMapping("/hi")
	public String helloWorld(){
		return ("Hello World from SpringBoot!");
	}

	@GetMapping("/bye")
	public String bye(){
		return ("Bye!");
	}

}
