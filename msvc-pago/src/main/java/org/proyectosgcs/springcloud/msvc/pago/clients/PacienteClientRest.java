package org.proyectosgcs.springcloud.msvc.pago.clients;

import org.proyectosgcs.springcloud.msvc.pago.models.Paciente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "pacienteClient", url = "localhost:8001/api/pacientes")
public interface PacienteClientRest {
    @GetMapping("/{id}")
    Optional<Paciente> obtenerPacientePorId(@PathVariable Long id);
}
