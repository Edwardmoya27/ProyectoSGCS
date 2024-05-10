package org.proyectosgcs.springcloud.msvc.medico.services;

import org.proyectosgcs.springcloud.msvc.medico.models.entity.Especialidad;
import org.proyectosgcs.springcloud.msvc.medico.repositories.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadServiceImpl implements EspecialidadService{

    @Autowired
    private EspecialidadRepository repository;

    @Override
    public List<Especialidad> listarEspecialidades() {
        return (List<Especialidad>)repository.findAll();
    }

    @Override
    public Optional<Especialidad> obtenerEspecialidad(Long id) {
        return repository.findById(id);
    }

    @Override
    public Especialidad registrarEspecialidad(Especialidad especialidad) {
        return repository.save(especialidad);
    }

    @Override
    public void eliminarEspecialidad(Long id) {
        repository.deleteById(id);
    }
}
