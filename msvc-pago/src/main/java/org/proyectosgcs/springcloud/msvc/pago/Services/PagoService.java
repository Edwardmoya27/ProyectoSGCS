package org.proyectosgcs.springcloud.msvc.pago.Services;

import org.proyectosgcs.springcloud.msvc.pago.models.entity.Pago;

import java.util.List;
import java.util.Optional;

/**
 * @file: PagoService
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 05:52 p. m.
 */
public interface PagoService {
    List<Pago> listarPagos();
    Optional<Pago> buscarPorIdPago(Long idPago);
    Pago guardarPago(Pago pago);
    void eliminarPago(long idPago);
}
