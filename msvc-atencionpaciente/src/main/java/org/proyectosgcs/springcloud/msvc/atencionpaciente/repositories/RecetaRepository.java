package org.proyectosgcs.springcloud.msvc.atencionpaciente.repositories;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.Receta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends CrudRepository<Receta, Long> {
}

