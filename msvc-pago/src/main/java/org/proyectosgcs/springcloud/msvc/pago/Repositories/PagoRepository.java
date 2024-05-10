package org.proyectosgcs.springcloud.msvc.pago.Repositories;

import org.proyectosgcs.springcloud.msvc.pago.models.entity.Pago;
import org.springframework.data.repository.CrudRepository;

/**
 * @file: PagoRepository
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 05:50 p. m.
 */
public interface PagoRepository extends CrudRepository<Pago, Long> {
}
