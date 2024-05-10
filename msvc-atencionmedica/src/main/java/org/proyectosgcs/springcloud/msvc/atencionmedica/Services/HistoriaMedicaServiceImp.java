package org.proyectosgcs.springcloud.msvc.atencionmedica.Services;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.HistoriaMedica;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Repositories.HistoriaMedicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @file: HistoriaMedicaServiceImp
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 05:24 p. m.
 */
@Service
public class HistoriaMedicaServiceImp implements HistoriaMedicaService{
    @Autowired
    private HistoriaMedicaRepository HistRep;

    @Override
    public List<HistoriaMedica> listarHistorias() {
        return (List<HistoriaMedica>)HistRep.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HistoriaMedica> buscarPorIdHistoria(Long idHistoria) {
        return HistRep.findById(idHistoria);
    }

    @Override
    public HistoriaMedica guardarHistoria(HistoriaMedica historiaMedica) {
        return HistRep.save(historiaMedica);
    }

    @Override
    public void eliminarHistoria(long idHistoria) {
        HistRep.deleteById(idHistoria);

    }

    @Override
    public Optional<HistoriaMedica> historiaMedicaPorIdPaciente(Long idPaciente) {
        return HistRep.obtenerHistoriaMedicaPorIdPaciente(idPaciente);
    }

}
