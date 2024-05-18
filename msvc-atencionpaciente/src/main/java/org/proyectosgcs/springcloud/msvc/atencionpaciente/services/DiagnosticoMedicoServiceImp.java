package org.proyectosgcs.springcloud.msvc.atencionpaciente.services;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.DiagnosticoMedico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.repositories.DiagnosticoMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class DiagnosticoMedicoServiceImp implements DiagnosticoMedicoService {

        @Autowired
        private DiagnosticoMedicoRepository diagnosticoMedicoRepository;

        @Override
        public DiagnosticoMedico saveDiagnosticoMedico(DiagnosticoMedico diagnosticoMedico) {
            return null;
        }

        @Override
        public Optional<DiagnosticoMedico> findById(Long id) {
            return diagnosticoMedicoRepository.findById(id);
        }

        @Override
        public List<DiagnosticoMedico> findAll() {
            Iterable<DiagnosticoMedico> iterable = diagnosticoMedicoRepository.findAll();
            return StreamSupport.stream(iterable.spliterator(), false)
                    .collect(Collectors.toList());
        }

        @Override
        public List<DiagnosticoMedico> findByPacienteId(Long pacienteId) {
            return diagnosticoMedicoRepository.findByIdPaciente(pacienteId);
        }

        @Override
        public List<DiagnosticoMedico> findByMedicoId(Long medicoId) {
            return diagnosticoMedicoRepository.findByIdMedico(medicoId);
        }

        @Override
        public DiagnosticoMedico updateDiagnosticoMedico(Long id, DiagnosticoMedico diagnosticoMedico) {
            Optional<DiagnosticoMedico> existingDiagnostico = diagnosticoMedicoRepository.findById(id);
            if (existingDiagnostico.isPresent()) {
                DiagnosticoMedico updatedDiagnostico = existingDiagnostico.get();
                updatedDiagnostico.setDescripcion(diagnosticoMedico.getDescripcion());
                updatedDiagnostico.setObservaciones(diagnosticoMedico.getObservaciones());
                // Puedes seguir actualizando otros campos necesarios aquí.
                return diagnosticoMedicoRepository.save(updatedDiagnostico);
            } else {
                throw new RuntimeException("Diagnóstico Médico no encontrado con el ID: " + id);
            }
        }

        @Override
        public void deleteDiagnosticoMedico(Long id) {
            diagnosticoMedicoRepository.deleteById(id);
        }
}
