package org.proyectosgcs.springcloud.msvc.atencionpaciente.services;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.Diagnostico;

import java.util.List;
import java.util.Optional;

public interface DiagnosticoService {

    Diagnostico saveDiagnostico(Diagnostico diagnostico);

    Optional<Diagnostico> findById(Long id);

    List<Diagnostico> findAll();

    List<Diagnostico> findByIdPaciente(Long idPaciente);

    List<Diagnostico> findByIdMedico(Long idMedico);

    Diagnostico updateDiagnosticoMedico(Long id, Diagnostico diagnosticoMedico);

    void deleteDiagnosticoMedico(Long id);
}

