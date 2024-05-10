package org.proyectosgcs.springcloud.msvc.medico.repositories;

import org.proyectosgcs.springcloud.msvc.medico.models.entity.Medico;
import org.springframework.data.repository.CrudRepository;

public interface MedicoRepository extends CrudRepository<Medico, Long> {
}
