package org.proyectosgcs.springcloud.msvc.atencionpaciente.repositories;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.Diagnostico;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticoRepository extends CrudRepository<Diagnostico, Long> {

    List<Diagnostico> findByIdPaciente(Long idPaciente);

    List<Diagnostico> findByIdMedico(Long idMedico);

}

