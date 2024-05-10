package org.proyectosgcs.springcloud.msvc.atencionpaciente.services;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.modules.entity.RecetaMedica;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.repositories.RecetaMedicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecetaMedicaServiceImp implements RecetaMedicaService{
    @Autowired
    private RecetaMedicaRepository recetaMedicaRepository;

    @Override
    public RecetaMedica saveRecetaMedica(RecetaMedica recetaMedica) {
        return recetaMedicaRepository.save(recetaMedica);
    }

    @Override
    public Optional<RecetaMedica> findById(Long id) {
        return recetaMedicaRepository.findById(id);
    }

    @Override
    public List<RecetaMedica> findAll() {
        Iterable<RecetaMedica> iterable = recetaMedicaRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecetaMedica> findByMedicoId(Long medicoId) {
        return recetaMedicaRepository.findByIdMedico(medicoId);
    }

    @Override
    public List<RecetaMedica> findByPacienteId(Long pacienteId) {
        return recetaMedicaRepository.findByIdPaciente(pacienteId);
    }

    @Override
    public RecetaMedica updateRecetaMedica(Long id, RecetaMedica recetaMedica) {
        Optional<RecetaMedica> existingReceta = recetaMedicaRepository.findById(id);
        if (existingReceta.isPresent()) {
            RecetaMedica updatedReceta = existingReceta.get();
            updatedReceta.setNombreMedicamento(recetaMedica.getNombreMedicamento());
            updatedReceta.setDosis(recetaMedica.getDosis());
            updatedReceta.setFrecuencia(recetaMedica.getFrecuencia());
            updatedReceta.setDuracion(recetaMedica.getDuracion());
            updatedReceta.setInstruccionesAdicionales(recetaMedica.getInstruccionesAdicionales());
            return recetaMedicaRepository.save(updatedReceta);
        } else {
            throw new RuntimeException("Receta Médica no encontrada con el ID: " + id);
        }
    }

    @Override
    public void deleteRecetaMedica(Long id) {
        recetaMedicaRepository.deleteById(id);
    }

    @Override
    public Object save(RecetaMedica recetaMedica) {
        return null;
    }
}
