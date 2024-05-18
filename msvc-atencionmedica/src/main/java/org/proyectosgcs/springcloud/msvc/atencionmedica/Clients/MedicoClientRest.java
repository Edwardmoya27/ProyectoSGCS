package org.proyectosgcs.springcloud.msvc.atencionmedica.Clients;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Medico;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "medicoClient", url = "localhost:8002/api/medicos")
public interface MedicoClientRest {
        @GetMapping("/{id}")
    Optional<Medico> obtenerMedicoPorId(@PathVariable Long id);
}
