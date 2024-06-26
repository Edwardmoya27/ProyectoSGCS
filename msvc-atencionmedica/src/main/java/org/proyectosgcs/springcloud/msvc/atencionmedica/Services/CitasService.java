package org.proyectosgcs.springcloud.msvc.atencionmedica.Services;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Cita;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Pago;

import java.util.List;
import java.util.Optional;

/**
 * @file: CitaService
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 05:22 p. m.
 */
public interface CitasService {
    List<Cita> listarCitas();
    Optional<Cita> buscarPorIdCitas(Long idCitas);
    Cita guardarCitas(Cita cita);
    void eliminarCitas(long idCitas);

    //otros metodos
    List<Cita> obtenerCitasPorIdPaciente(Long id);

    List<Cita> obtenerCitasPorIdMedico(Long id);

}
