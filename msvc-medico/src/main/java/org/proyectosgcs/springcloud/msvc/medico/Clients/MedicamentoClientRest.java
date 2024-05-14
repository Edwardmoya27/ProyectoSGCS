package org.proyectosgcs.springcloud.msvc.medico.Clients;

import org.proyectosgcs.springcloud.msvc.medico.models.Medicamento;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "medicamentoClient", url = "localhost:8004/api/medicamentos")
public interface MedicamentoClientRest {
    @GetMapping
    List<Medicamento> obtenerListadoMedicamentos();

}
