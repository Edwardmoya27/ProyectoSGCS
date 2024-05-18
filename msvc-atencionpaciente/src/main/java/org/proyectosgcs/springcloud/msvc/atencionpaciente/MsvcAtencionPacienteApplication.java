package org.proyectosgcs.springcloud.msvc.atencionpaciente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcAtencionPacienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcAtencionPacienteApplication.class, args);
	}

}
