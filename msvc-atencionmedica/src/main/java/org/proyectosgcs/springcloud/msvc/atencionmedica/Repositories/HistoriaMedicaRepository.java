package org.proyectosgcs.springcloud.msvc.atencionmedica.Repositories;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.HistoriaMedica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @file: HistoriaMedicaRepository
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 05:18 p. m.
 */
public interface HistoriaMedicaRepository extends CrudRepository<HistoriaMedica, Long> {

    @Query("select hm from HistoriaMedica hm where hm.paciente.id = ?1")
    Optional<HistoriaMedica> obtenerHistoriaMedicaPorIdPaciente(Long Id);

}
