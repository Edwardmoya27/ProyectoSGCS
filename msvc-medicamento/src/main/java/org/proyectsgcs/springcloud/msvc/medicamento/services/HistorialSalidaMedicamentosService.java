package org.proyectsgcs.springcloud.msvc.medicamento.services;

import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.HistorialSalidaMedicamentos;

import java.util.List;
import java.util.Optional;

public interface HistorialSalidaMedicamentosService {
    List<HistorialSalidaMedicamentos> listar();
    Optional<HistorialSalidaMedicamentos> porId(Long id);
    HistorialSalidaMedicamentos guardar(HistorialSalidaMedicamentos estudiante);
    void eliminar (Long id);
}
