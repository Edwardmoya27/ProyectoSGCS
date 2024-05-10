package org.proyectosgcs.springcloud.msvc.pago.Services;

import org.proyectosgcs.springcloud.msvc.pago.Repositories.PagoRepository;
import org.proyectosgcs.springcloud.msvc.pago.models.entity.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @file: PagoServiceImp
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 05:52 p. m.
 */
@Service
public class PagoServiceImp implements PagoService{
    @Autowired
    private PagoRepository pagoRep;

    @Override
    public List<Pago> listarPagos() {
        return (List<Pago>)pagoRep.findAll();
    }

    @Override
    public Optional<Pago> buscarPorIdPago(Long idPago) {
        return pagoRep.findById(idPago);
    }

    @Override
    public Pago guardarPago(Pago pago) {
        return pagoRep.save(pago);
    }

    @Override
    public void eliminarPago(long idPago) {
        pagoRep.deleteById(idPago);

    }
}
