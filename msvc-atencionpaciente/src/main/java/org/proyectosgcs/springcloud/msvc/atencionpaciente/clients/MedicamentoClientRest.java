package org.proyectosgcs.springcloud.msvc.atencionpaciente.clients;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.config.FeignClientConfig;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Medicamento;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

//@FeignClient(name = "medicamentoClient", url = "localhost:8004/api/medicamentos", configuration = FeignClientConfig.class)
@FeignClient(name = "medicamentoClient", url = "https://proyectosgcs-medicamento.onrender.com/api/medicamentos", configuration = FeignClientConfig.class)

public interface MedicamentoClientRest {
    @GetMapping("/{id}")
    Optional<Medicamento> obtenerMedicamentoPorId(@PathVariable Long id);
}
