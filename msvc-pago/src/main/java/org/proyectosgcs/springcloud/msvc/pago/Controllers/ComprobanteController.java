package org.proyectosgcs.springcloud.msvc.pago.Controllers;

import org.proyectosgcs.springcloud.msvc.pago.Services.ComprobanteService;
import org.proyectosgcs.springcloud.msvc.pago.Services.PagoService;
import org.proyectosgcs.springcloud.msvc.pago.models.entity.Comprobante;
import org.proyectosgcs.springcloud.msvc.pago.models.entity.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @file: ComprobanteController
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 05:53 p. m.
 */
@RestController
@RequestMapping("api/comprobantes")
public class ComprobanteController {
    @Autowired
    private ComprobanteService compService;

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Comprobante> listarComprobantes(){
        return compService.listarComprobantes();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorIdComprobante(@PathVariable Long id){
        Optional<Comprobante> PacienteOptional = compService.buscarPorIdComprobante(id);
        if(PacienteOptional.isPresent()) {
            return ResponseEntity.ok(PacienteOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    //otros metodos

    @PostMapping("/generar")
    public ResponseEntity<?> generarComprobante(@RequestBody Map<String, Long> data) {


        Optional<Pago> pagoOptional = pagoService.buscarPorIdPago(data.get("idPago"));
        if (pagoOptional.isPresent()) {
            Pago pago = pagoOptional.get();
            Comprobante comprobante = compService.generarComprobante(pago);
            return ResponseEntity.status(HttpStatus.CREATED).body(comprobante);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
