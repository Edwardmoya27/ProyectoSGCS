package org.proyectsgcs.springcloud.msvc.medicamento.services;

import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.CategoriaMedicamento;

import java.util.List;
import java.util.Optional;

public interface CategoriaMedicamentoService {
    List<CategoriaMedicamento> listar();
    Optional<CategoriaMedicamento> porId(Long id);
    CategoriaMedicamento guardar(CategoriaMedicamento categoriaMedicamento);
    void eliminar(Long id);
}
