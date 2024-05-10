package org.proyectosgcs.springcloud.msvc.atencionmedica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcAtencionMedica {

	public static void main(String[] args) {
		SpringApplication.run(MsvcAtencionMedica.class, args);
	}

}
