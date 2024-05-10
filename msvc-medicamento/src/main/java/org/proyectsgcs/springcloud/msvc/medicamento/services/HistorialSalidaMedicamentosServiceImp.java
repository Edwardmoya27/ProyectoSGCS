package org.proyectsgcs.springcloud.msvc.medicamento.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.HistorialSalidaMedicamentos;
import org.proyectsgcs.springcloud.msvc.medicamento.repositories.HistorialSalidaMedicamentosRepository;

import java.util.List;
import java.util.Optional;
@Service
public class HistorialSalidaMedicamentosServiceImp implements HistorialSalidaMedicamentosService {
    @Autowired
    private HistorialSalidaMedicamentosRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<HistorialSalidaMedicamentos> listar() {
        return (List<HistorialSalidaMedicamentos>) repository.findAll();    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HistorialSalidaMedicamentos> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public HistorialSalidaMedicamentos guardar(HistorialSalidaMedicamentos estudiante) {
        return repository.save(estudiante);    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
