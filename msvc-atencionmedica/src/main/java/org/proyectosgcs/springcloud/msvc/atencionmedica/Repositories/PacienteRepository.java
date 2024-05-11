package org.proyectosgcs.springcloud.msvc.atencionmedica.Repositories;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Paciente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @file: Paciente
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 03:59 p. m.
 */
public interface PacienteRepository extends CrudRepository<Paciente, Long> {
    @Query("SELECT p FROM Paciente p WHERE p.dni = ?1")
    Optional<Paciente> obtenerPacientePorDni(String dni);
}
