package org.proyectosgcs.springcloud.msvc.atencionpaciente.services;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.Receta;

import java.util.List;
import java.util.Optional;

public interface RecetaService {

    Receta saveReceta(Receta receta);

    Optional<Receta> findById(Long id);

    List<Receta> findAll();

    Receta updateRecetaMedica(Long id, Receta recetaMedica);

    void deleteRecetaMedica(Long id);

    Object save(Receta recetaMedica);

    //otros metodos
    Receta crearRecetaParaDiagnostico(Long idDiagnostico, Receta recetaMedica);


}

