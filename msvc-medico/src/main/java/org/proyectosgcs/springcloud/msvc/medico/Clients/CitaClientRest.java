package org.proyectosgcs.springcloud.msvc.medico.Clients;

import org.proyectosgcs.springcloud.msvc.medico.config.FeignClientConfig;
import org.proyectosgcs.springcloud.msvc.medico.models.Cita;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(name = "citaClient", url = "localhost:8001/api/citas", configuration = FeignClientConfig.class)
@FeignClient(name = "citaClient", url = "https://proyectosgcs-atencionmedica.onrender.com/api/citas", configuration = FeignClientConfig.class)

public interface CitaClientRest {
    @GetMapping("/medico/{id}")
    List<Cita> obtenerCitasPorIdMedico(@PathVariable Long id);
}
