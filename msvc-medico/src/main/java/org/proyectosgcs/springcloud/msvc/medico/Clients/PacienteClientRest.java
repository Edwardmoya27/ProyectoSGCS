package org.proyectosgcs.springcloud.msvc.medico.Clients;

import org.proyectosgcs.springcloud.msvc.medico.models.Paciente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pacienteClient", url = "localhost:8001/api/pacientes")
public interface PacienteClientRest {
    @GetMapping("/{id}")
    Paciente obtenerPacientePorId(@PathVariable Long id);

}
