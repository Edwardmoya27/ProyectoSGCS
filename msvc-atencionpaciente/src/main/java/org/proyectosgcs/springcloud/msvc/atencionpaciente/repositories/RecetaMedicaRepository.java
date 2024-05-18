package org.proyectosgcs.springcloud.msvc.atencionpaciente.repositories;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.RecetaMedica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaMedicaRepository extends CrudRepository<RecetaMedica, Long> {
    List<RecetaMedica> findByIdMedico(Long medicoId);

    List<RecetaMedica> findByIdPaciente(Long pacienteId);
    // Aquí puedes agregar métodos de consulta personalizados si lo necesitas
}

