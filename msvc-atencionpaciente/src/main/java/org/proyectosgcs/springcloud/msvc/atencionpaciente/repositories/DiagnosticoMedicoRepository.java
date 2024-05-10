package org.proyectosgcs.springcloud.msvc.atencionpaciente.repositories;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.modules.entity.DiagnosticoMedico;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticoMedicoRepository extends CrudRepository<DiagnosticoMedico, Long> {
    // Método para encontrar diagnósticos por el ID del paciente
    List<DiagnosticoMedico> findByIdPaciente(Long idPaciente);

    // Método para encontrar diagnósticos por el ID del médico
    List<DiagnosticoMedico> findByIdMedico(Long idMedico);
}

