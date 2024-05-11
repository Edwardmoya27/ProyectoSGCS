package org.proyectosgcs.springcloud.msvc.atencionmedica.Repositories;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Cita;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.HistoriaMedica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @file: CitasRepository
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 05:17 p. m.
 */
public interface CitasRepository extends CrudRepository<Cita, Long> {
    @Query("select c from Cita c where c.paciente.id = ?1")
    List<Cita> obtenerCitasPorIdPaciente(Long idPaciente);

    @Query("select c from Cita c where c.medicoId = ?1")
    List<Cita> obtenerCitasPorIdMedico(Long idMedico);

}
