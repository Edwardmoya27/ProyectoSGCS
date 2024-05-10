package org.proyectosgcs.springcloud.msvc.pago.Controllers;
import org.proyectosgcs.springcloud.msvc.pago.Services.PagoService;

import org.proyectosgcs.springcloud.msvc.pago.models.entity.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @file: PagoController
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 05:54 p. m.
 */
@RestController
@RequestMapping("api/pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> listarPagos(){
        return pagoService.listarPagos();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorIdPago(@PathVariable Long id){
        Optional<Pago> PagoOptional = pagoService.buscarPorIdPago(id);
        if(PagoOptional.isPresent()) {
            return ResponseEntity.ok(PagoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<?> guardarPago(@RequestBody Pago pago){
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.guardarPago(pago));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarPago(@PathVariable Long id,@RequestBody Pago pago){
        Optional<Pago> o = pagoService.buscarPorIdPago(id);
        if(o.isPresent()) {
            Pago pagoDB = o.get();
            pagoDB.setIdPaciente(pago.getIdPaciente());
            pagoDB.setMonto(pago.getMonto());
            pagoDB.setFechaPago(pago.getFechaPago());
            pagoDB.setMetodoPago(pago.getMetodoPago());
            return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.guardarPago(pagoDB));
        }
        return ResponseEntity.ok().body(Map.of("status", "error", "message",
                "No se ha encontrado el ID del paciente"));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> eliminarPago(@PathVariable Long id){
        Optional<Pago> o = pagoService.buscarPorIdPago(id);
        if(o.isPresent()) {
            pagoService.eliminarPago(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
