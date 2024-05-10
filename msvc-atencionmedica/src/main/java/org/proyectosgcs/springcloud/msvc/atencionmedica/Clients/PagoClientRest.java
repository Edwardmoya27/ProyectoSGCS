package org.proyectosgcs.springcloud.msvc.atencionmedica.Clients;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Pago;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @file: PagoClientRest
 * @author: EdwarMoya
 * @created: 08/05/2024
 * @HoraCreated: 09:27 p. m.
 */
@FeignClient(name = "msvc-pagos", url = "localhost:8002/api/pagos")
public interface PagoClientRest {
    @GetMapping("/{id}")
    Pago detalle(@PathVariable Long id);

    @PostMapping
    Pago crear(@RequestBody Pago pago);

}
