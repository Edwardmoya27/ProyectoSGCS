package org.proyectosgcs.springcloud.msvc.atencionpaciente.clients;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Medico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Paciente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "medicoClient", url = "localhost:8002/api/medicos")
public interface MedicoClientRest {
    @GetMapping("/{id}")
    Optional<Medico> obtenerMedicoPorId(@PathVariable Long id);
}
