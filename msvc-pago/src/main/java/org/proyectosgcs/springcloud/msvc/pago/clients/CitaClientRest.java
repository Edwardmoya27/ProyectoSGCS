package org.proyectosgcs.springcloud.msvc.pago.clients;

import org.proyectosgcs.springcloud.msvc.pago.models.Cita;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "citaClient", url = "localhost:8001/api/citas")
public interface CitaClientRest {
        @GetMapping("/{id}")
    Optional<Cita> obtenerCitaPorId(@PathVariable Long id);
}
