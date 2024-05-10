package org.proyectsgcs.springcloud.msvc.medicamento.services;

import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.Medicamento;

import java.util.*;

public interface MedicamentoService {
    List <Medicamento> listar();
    Optional<Medicamento>porId(Long id);
    Medicamento guardar(Medicamento medicamento);
    void eliminar(Long id);
}
