package org.proyectosgcs.springcloud.msvc.medico.Clients;

import org.proyectosgcs.springcloud.msvc.medico.models.Cita;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "citaClient", url = "localhost:8001/api/citas")
public interface CitaClientRest {
    @GetMapping("/{id}")
    Cita obtenerCitaPorId(@PathVariable Long id);

    @GetMapping()
    List<Cita> obtenerCitasPorIdMedico(@PathVariable Long id);
}
