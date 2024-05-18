package org.proyectosgcs.springcloud.msvc.atencionmedica.Clients;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Pago;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

/**
 * @file: PagoClientRest
 * @author: EdwarMoya
 * @created: 08/05/2024
 * @HoraCreated: 09:27 p. m.
 */
@FeignClient(name = "pagoClient", url = "localhost:8003/api/pagos")
public interface PagoClientRest {
    @GetMapping("/{id}")
    Optional<Pago> obtenerPagoPorId(@PathVariable Long id);

    @PostMapping
    Optional<Pago> crear(@RequestBody Pago pago);

}
