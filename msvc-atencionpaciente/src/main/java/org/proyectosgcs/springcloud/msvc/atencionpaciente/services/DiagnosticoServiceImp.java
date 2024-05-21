package org.proyectosgcs.springcloud.msvc.atencionpaciente.services;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.Diagnostico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.repositories.DiagnosticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class DiagnosticoServiceImp implements DiagnosticoService {

        @Autowired
        private DiagnosticoRepository diagnosticoRepository;

        @Override
        public Diagnostico saveDiagnostico(Diagnostico diagnostico) {
            return diagnosticoRepository.save(diagnostico);
        }

        @Override
        public Optional<Diagnostico> findById(Long id) {
            return diagnosticoRepository.findById(id);
        }

        @Override
        public List<Diagnostico> findAll() {
            return (List<Diagnostico>)diagnosticoRepository.findAll();
        }

    @Override
    public List<Diagnostico> findByIdPaciente(Long idPaciente) {
        return diagnosticoRepository.findByIdPaciente(idPaciente);
    }

    @Override
    public List<Diagnostico> findByIdMedico(Long idMedico) {
        return diagnosticoRepository.findByIdMedico(idMedico);
    }

    @Override
        public Diagnostico updateDiagnosticoMedico(Long id, Diagnostico diagnosticoMedico) {
            Optional<Diagnostico> existingDiagnostico = diagnosticoRepository.findById(id);
            if (existingDiagnostico.isPresent()) {
                Diagnostico updatedDiagnostico = existingDiagnostico.get();
                updatedDiagnostico.setDescripcion(diagnosticoMedico.getDescripcion());
                updatedDiagnostico.setObservaciones(diagnosticoMedico.getObservaciones());
                // Puedes seguir actualizando otros campos necesarios aquí.
                return diagnosticoRepository.save(updatedDiagnostico);
            } else {
                throw new RuntimeException("Diagnóstico Médico no encontrado con el ID: " + id);
            }
        }

        @Override
        public void deleteDiagnosticoMedico(Long id) {
            diagnosticoRepository.deleteById(id);
        }
}
