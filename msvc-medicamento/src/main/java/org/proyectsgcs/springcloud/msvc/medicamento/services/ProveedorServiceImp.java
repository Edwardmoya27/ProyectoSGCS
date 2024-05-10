package org.proyectsgcs.springcloud.msvc.medicamento.services;

import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.Proveedor;
import org.proyectsgcs.springcloud.msvc.medicamento.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service
public class ProveedorServiceImp implements ProveedorService{
    @Autowired
    private ProveedorRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> listar() {
        return (List<Proveedor>) repository.findAll();
    }

    @Override
    @Transactional (readOnly = true)
    public Optional<Proveedor> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Proveedor guardar(Proveedor medicamento) {
        return repository.save(medicamento);    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
