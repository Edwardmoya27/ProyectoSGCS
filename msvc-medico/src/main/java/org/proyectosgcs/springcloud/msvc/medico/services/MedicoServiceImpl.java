package org.proyectosgcs.springcloud.msvc.medico.services;

import org.proyectosgcs.springcloud.msvc.medico.models.entity.Medico;
import org.proyectosgcs.springcloud.msvc.medico.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoServiceImpl implements MedicoService{

    @Autowired
    private MedicoRepository repository;

    @Override
    public List<Medico> listarMedicos() {
        return (List<Medico>)repository.findAll();
    }

    @Override
    public Optional<Medico> obtenerMedicoPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Medico registrarMedico(Medico medico) {
        return repository.save(medico);
    }


    @Override
    public void eliminarMedico(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Medico> obtenerMedicoPorDni(String dni) {
        return repository.obtenerMedicoPorDni(dni);
    }
}
