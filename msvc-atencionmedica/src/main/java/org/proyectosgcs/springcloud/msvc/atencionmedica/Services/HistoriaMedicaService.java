package org.proyectosgcs.springcloud.msvc.atencionmedica.Services;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.HistoriaMedica;

import java.util.List;
import java.util.Optional;

/**
 * @file: HistoriaMedicaService
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 05:23 p. m.
 */
public interface HistoriaMedicaService {
    List<HistoriaMedica> listarHistorias();
    Optional<HistoriaMedica> buscarPorIdHistoria(Long idHistoria);
    HistoriaMedica guardarHistoria(HistoriaMedica historiaMedica);
    void eliminarHistoria(long idHistoria);

    //Otros Metodos

    Optional<HistoriaMedica> historiaMedicaPorIdPaciente(Long idPaciente);

}
