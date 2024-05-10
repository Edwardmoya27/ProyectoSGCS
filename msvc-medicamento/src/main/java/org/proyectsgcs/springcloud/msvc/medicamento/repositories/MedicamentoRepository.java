package org.proyectsgcs.springcloud.msvc.medicamento.repositories;

import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.Medicamento;
import org.springframework.data.repository.CrudRepository;

public interface MedicamentoRepository extends CrudRepository<Medicamento, Long> {
}
