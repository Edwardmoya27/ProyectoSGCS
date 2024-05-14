package org.proyectosgcs.springcloud.msvc.medico.repositories;

import org.proyectosgcs.springcloud.msvc.medico.models.entity.Medico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MedicoRepository extends CrudRepository<Medico, Long> {
    @Query("SELECT m FROM Medico m WHERE m.dni = ?1")
    Optional<Medico> obtenerMedicoPorDni(String dni);

}
