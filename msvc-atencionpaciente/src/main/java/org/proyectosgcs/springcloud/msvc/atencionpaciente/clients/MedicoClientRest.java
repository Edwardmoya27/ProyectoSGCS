package org.proyectosgcs.springcloud.msvc.atencionpaciente.clients;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.config.FeignClientConfig;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Medico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Paciente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

//@FeignClient(name = "medicoClient", url = "localhost:8002/api/medicos", configuration = FeignClientConfig.class)
@FeignClient(name = "medicoClient", url = "https://proyectosgcs-medico.onrender.com/api/medicos", configuration = FeignClientConfig.class)
public interface MedicoClientRest {
    @GetMapping("/{id}")
    Optional<Medico> obtenerMedicoPorId(@PathVariable Long id);
}


