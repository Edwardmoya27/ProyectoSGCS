package org.proyectosgcs.springcloud.msvc.medico.services;

import org.proyectosgcs.springcloud.msvc.medico.models.entity.Medico;

import java.util.List;
import java.util.Optional;

public interface MedicoService {
    List<Medico> listarMedicos();
    Optional<Medico> obtenerMedicoPorId(Long id);
    Medico registrarMedico(Medico medico);
    void eliminarMedico(Long id);

    Optional<Medico> obtenerMedicoPorDni(String dni);
}
