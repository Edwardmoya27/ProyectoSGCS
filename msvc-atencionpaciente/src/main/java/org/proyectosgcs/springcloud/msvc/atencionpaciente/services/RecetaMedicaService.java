package org.proyectosgcs.springcloud.msvc.atencionpaciente.services;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.modules.entity.RecetaMedica;

import java.util.List;
import java.util.Optional;

public interface RecetaMedicaService {

    RecetaMedica saveRecetaMedica(RecetaMedica recetaMedica);

    Optional<RecetaMedica> findById(Long id);

    List<RecetaMedica> findAll();

    List<RecetaMedica> findByMedicoId(Long medicoId);

    List<RecetaMedica> findByPacienteId(Long pacienteId);

    RecetaMedica updateRecetaMedica(Long id, RecetaMedica recetaMedica);

    void deleteRecetaMedica(Long id);

    Object save(RecetaMedica recetaMedica);


    //otros metodos
    RecetaMedica crearRecetaParaDiagnostico(Long idDiagnostico, RecetaMedica recetaMedica);


}

