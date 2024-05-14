package org.proyectosgcs.springcloud.msvc.atencionmedica.Services;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Clients.PagoClientRest;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Cita;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Pago;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Repositories.CitasRepository;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @file: CitaServiceImp
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 05:22 p. m.
 */
@Service
public class CitasServiceImp implements CitasService {
    @Autowired
    private CitasRepository citasRepository;
    @Autowired
    private PagoClientRest client;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<Cita> listarCitas() {
        return (List<Cita>)citasRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cita> buscarPorIdCitas(Long idCitas) {
        return citasRepository.findById(idCitas);
    }

    @Override
    public Cita guardarCitas(Cita cita) {
       // Pago pago = crearPago(new Pago(),cita.getId()).get();
        //CitaPago citaPago = new CitaPago();
        //citaPago.setCita(cita);
       // citaPago.setPago(pago);
       // cita.setCitaPago(citaPago);
        return citasRepository.save(cita);
    }

    @Override
    public void eliminarCitas(long idCitas) {
        citasRepository.deleteById(idCitas);
    }

    @Override
    @Transactional
    public Optional<Pago> asignarPago(Pago pago, Long citaId) {
        Optional<Cita> cita = citasRepository.findById(citaId);
        if (cita.isPresent()){
            Pago pagoDB = client.detalle(pago.getId());
            Cita citaDB = cita.get();
            citasRepository.save(citaDB);
            return Optional.of(pagoDB);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Pago> crearPago(Pago pago, Long citaId) {
        Optional<Pago> pagoOptional = client.crear(pago);
        if (pagoOptional.isPresent()){
            return Optional.of(pagoOptional.get());
        }
        return Optional.empty();
    }

    @Override
    public List<Cita> obtenerCitasPorIdPaciente(Long id) {
        return citasRepository.obtenerCitasPorIdPaciente(id);
    }

    @Override
    public List<Cita> obtenerCitasPorIdMedico(Long idMedico) {
        return citasRepository.obtenerCitasPorIdMedico(idMedico);
    }
}
