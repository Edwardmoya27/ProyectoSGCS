package org.proyectosgcs.springcloud.msvc.medico.services;

import org.proyectosgcs.springcloud.msvc.medico.models.entity.Especialidad;

import java.util.List;
import java.util.Optional;

public interface EspecialidadService {
    List<Especialidad> listarEspecialidades();
    Optional<Especialidad> obtenerEspecialidad(Long id);
    Especialidad registrarEspecialidad(Especialidad medico);
    void eliminarEspecialidad(Long id);
}
