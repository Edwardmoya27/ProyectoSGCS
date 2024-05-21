package org.proyectosgcs.springcloud.msvc.atencionpaciente.clients;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.config.FeignClientConfig;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Paciente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

//@FeignClient(name = "pacienteClient", url = "localhost:8001/api/pacientes", configuration = FeignClientConfig.class)
@FeignClient(name = "pacienteClient", url = "https://proyectosgcs-atencionmedica.onrender.com/api/pacientes", configuration = FeignClientConfig.class)
public interface PacienteClientRest {
    @GetMapping("/{id}")
    Optional<Paciente> obtenerPacientePorId(@PathVariable Long id);
}
