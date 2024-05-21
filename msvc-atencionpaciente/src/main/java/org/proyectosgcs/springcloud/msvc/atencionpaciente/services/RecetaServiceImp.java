package org.proyectosgcs.springcloud.msvc.atencionpaciente.services;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.Diagnostico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.Receta;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.repositories.DiagnosticoRepository;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.repositories.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecetaServiceImp implements RecetaService {
    @Autowired
    private RecetaRepository recetaRepository;
    @Autowired
    private DiagnosticoRepository diagMedRep;

    @Override
    public Receta saveReceta(Receta receta) {
        return recetaRepository.save(receta);
    }

    @Override
    public Optional<Receta> findById(Long id) {
        return recetaRepository.findById(id);
    }

    @Override
    public List<Receta> findAll() {
        return (List<Receta>) recetaRepository.findAll();
    }

    @Override
    public Receta updateRecetaMedica(Long id, Receta recetaMedica) {
        Optional<Receta> existingReceta = recetaRepository.findById(id);
        if (existingReceta.isPresent()) {
            Receta updatedReceta = existingReceta.get();
            updatedReceta.setIdMedicamento(recetaMedica.getIdMedicamento());
            updatedReceta.setDosis(recetaMedica.getDosis());
            updatedReceta.setFrecuencia(recetaMedica.getFrecuencia());
            updatedReceta.setDuracion(recetaMedica.getDuracion());
            updatedReceta.setInstrucciones(recetaMedica.getInstrucciones());
            return recetaRepository.save(updatedReceta);
        } else {
            throw new RuntimeException("Receta Médica no encontrada con el ID: " + id);
        }
    }

    @Override
    public void deleteRecetaMedica(Long id) {
        recetaRepository.deleteById(id);
    }

    @Override
    public Object save(Receta recetaMedica) {
        return null;
    }

    @Override
    public Receta crearRecetaParaDiagnostico(Long idDiagnostico, Receta recetaMedica) {
        Optional<Diagnostico> diagnosticoOptional = diagMedRep.findById(idDiagnostico);
        if (diagnosticoOptional.isPresent()) {
            Diagnostico diagnosticoMedico = diagnosticoOptional.get();

            // Asignar los atributos de la receta médica
            recetaMedica.setIdPaciente(diagnosticoMedico.getIdPaciente());
            recetaMedica.setIdMedico(diagnosticoMedico.getIdMedico());

            // Guardar la receta médica asociada al diagnóstico médico
        }
        return recetaRepository.save(recetaMedica);

    }

}
