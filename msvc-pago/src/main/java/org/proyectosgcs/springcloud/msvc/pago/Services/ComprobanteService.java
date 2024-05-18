package org.proyectosgcs.springcloud.msvc.pago.Services;

import org.proyectosgcs.springcloud.msvc.pago.models.entity.Comprobante;

import java.util.List;
import java.util.Optional;

/**
 * @file: ComprobanteService
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 05:52 p. m.
 */
public interface ComprobanteService {
    List<Comprobante> listarComprobantes();
    Optional<Comprobante> buscarPorIdComprobante(Long idComprobante);

    //otros metodos
    Comprobante generarComprobante(Comprobante comprobante);

}
