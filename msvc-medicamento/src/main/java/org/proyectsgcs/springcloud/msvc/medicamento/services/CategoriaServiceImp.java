package org.proyectsgcs.springcloud.msvc.medicamento.services;

import org.proyectsgcs.springcloud.msvc.medicamento.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.Categoria;

import java.util.List;
import java.util.Optional;
@Service
public class CategoriaServiceImp implements CategoriaService{
    @Autowired
    private CategoriaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> listar() {
        return (List<Categoria>) repository.findAll();    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Categoria> porId(Long id) {
        return repository.findById(id);    }

    @Override
    @Transactional
    public Categoria guardar(Categoria categoriaMedicamento) {
        return repository.save(categoriaMedicamento);    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
