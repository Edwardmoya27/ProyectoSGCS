package org.proyectosgcs.springcloud.msvc.pago.Services;

import org.proyectosgcs.springcloud.msvc.pago.Repositories.ComprobanteRepository;
import org.proyectosgcs.springcloud.msvc.pago.models.entity.Comprobante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @file: ComprobanteServiceImp
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 05:53 p. m.
 */
@Service
public class ComprobanteServiceImp implements ComprobanteService{
    @Autowired
    private ComprobanteRepository compRep;

    @Override
    public List<Comprobante> listarComprobantes() {
        return (List<Comprobante>)compRep.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comprobante> buscarPorIdComprobante(Long idComprobante) {
        return compRep.findById(idComprobante);
    }

    @Override
    @Transactional(readOnly = true)
    public Comprobante guardarComprobante(Comprobante comprobante) {
        return compRep.save(comprobante);
    }

    @Override
    public void eliminarComprobante(long idComprobante) {
        compRep.deleteById(idComprobante);
    }
}
