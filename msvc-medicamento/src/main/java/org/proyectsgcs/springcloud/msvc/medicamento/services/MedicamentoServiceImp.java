package org.proyectsgcs.springcloud.msvc.medicamento.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.Medicamento;
import org.proyectsgcs.springcloud.msvc.medicamento.repositories.MedicamentoRepository;

import java.util.*;
@Service
public class MedicamentoServiceImp implements MedicamentoService{
    @Autowired
    private MedicamentoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> listar() {
        return (List<Medicamento>) repository.findAll();    }

    @Override
    @Transactional (readOnly = true)
    public Optional<Medicamento> porId(Long id) {
        return repository.findById(id);    }

    @Override
    @Transactional
    public Medicamento guardar(Medicamento medicamento) {
        return repository.save(medicamento);    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

}
