package org.proyectosgcs.springcloud.msvc.pago.Repositories;

import org.proyectosgcs.springcloud.msvc.pago.models.entity.Comprobante;
import org.springframework.data.repository.CrudRepository;

/**
 * @file: ComprobanteRepository
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 05:51 p. m.
 */
public interface ComprobanteRepository extends CrudRepository<Comprobante, Long> {
}
