package org.proyectosgcs.springcloud.msvc.medico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcMedicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcMedicoApplication.class, args);
	}

}
