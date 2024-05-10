package org.proyectsgcs.springcloud.msvc.medicamento.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.CategoriaMedicamento;
import org.proyectsgcs.springcloud.msvc.medicamento.repositories.CategoriaMedicamentoRepository;

import java.util.List;
import java.util.Optional;
@Service
public class CategoriaMedicamentoServiceImp implements CategoriaMedicamentoService{
    @Autowired
    private CategoriaMedicamentoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaMedicamento> listar() {
        return (List<CategoriaMedicamento>) repository.findAll();    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaMedicamento> porId(Long id) {
        return repository.findById(id);    }

    @Override
    @Transactional
    public CategoriaMedicamento guardar(CategoriaMedicamento categoriaMedicamento) {
        return repository.save(categoriaMedicamento);    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
