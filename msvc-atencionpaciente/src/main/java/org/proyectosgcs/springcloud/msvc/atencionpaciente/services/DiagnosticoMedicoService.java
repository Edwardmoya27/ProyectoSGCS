package org.proyectosgcs.springcloud.msvc.atencionpaciente.services;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.DiagnosticoMedico;

import java.util.List;
import java.util.Optional;

public interface DiagnosticoMedicoService {

    DiagnosticoMedico saveDiagnosticoMedico(DiagnosticoMedico diagnosticoMedico);

    Optional<DiagnosticoMedico> findById(Long id);

    List<DiagnosticoMedico> findAll();

    List<DiagnosticoMedico> findByPacienteId(Long pacienteId);

    List<DiagnosticoMedico> findByMedicoId(Long medicoId);

    DiagnosticoMedico updateDiagnosticoMedico(Long id, DiagnosticoMedico diagnosticoMedico);

    void deleteDiagnosticoMedico(Long id);
}

