package org.proyectosgcs.springcloud.msvc.atencionmedica.Services;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Paciente;

import java.util.List;
import java.util.Optional;

/**
 * @file: PacienteService
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 03:59 p. m.
 */
public interface PacienteService {
    List<Paciente> listarPacientes();
    Optional<Paciente> buscarPorIdPaciente(Long idPaciente);
    Paciente guardarPaciente(Paciente paciente);
    void eliminarPaciente(long idPaciente);
}
