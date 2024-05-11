package org.proyectosgcs.springcloud.msvc.atencionmedica.Services;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Paciente;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @file: PacienteServiceImp
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 03:59 p. m.
 */
@Service
public class PacienteServiceImp implements PacienteService {
    @Autowired
    private PacienteRepository pacienteRep;


    @Override
    public List<Paciente> listarPacientes() {
        return (List<Paciente>) pacienteRep.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorIdPaciente(Long idPaciente) {
        return pacienteRep.findById(idPaciente);

    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRep.save(paciente);
    }

    @Override
    public void eliminarPaciente(long idPaciente) {
        pacienteRep.deleteById(idPaciente);

    }

    @Override
    public Optional<Paciente> obtenerPacientePorDni(String dni) {
        return pacienteRep.obtenerPacientePorDni(dni);
    }

}
