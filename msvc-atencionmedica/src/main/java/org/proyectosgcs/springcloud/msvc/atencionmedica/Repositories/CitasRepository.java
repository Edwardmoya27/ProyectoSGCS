package org.proyectosgcs.springcloud.msvc.atencionmedica.Repositories;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Cita;
import org.springframework.data.repository.CrudRepository;

/**
 * @file: CitasRepository
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 05:17 p. m.
 */
public interface CitasRepository extends CrudRepository <Cita, Long> {
}
