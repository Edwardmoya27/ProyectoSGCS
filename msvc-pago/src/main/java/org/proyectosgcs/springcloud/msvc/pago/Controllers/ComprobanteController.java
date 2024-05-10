package org.proyectosgcs.springcloud.msvc.pago.Controllers;

import org.proyectosgcs.springcloud.msvc.pago.Services.ComprobanteService;
import org.proyectosgcs.springcloud.msvc.pago.models.entity.Comprobante;
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
    @PostMapping
    public ResponseEntity<?> guardarComprobante(@RequestBody Comprobante comprobante){
        return ResponseEntity.status(HttpStatus.CREATED).body(compService.guardarComprobante(comprobante));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarComprobante(@PathVariable Long id,@RequestBody Comprobante comprobante){
        Optional<Comprobante> o = compService.buscarPorIdComprobante(id);
        if(o.isPresent()) {
            Comprobante compDB = o.get();
            compDB.setIdPago(compDB.getIdPago());
            compDB.setFechaEmision(compDB.getFechaEmision());
            compDB.setMonto(compDB.getMonto());
            return ResponseEntity.status(HttpStatus.CREATED).body(compService.guardarComprobante(compDB));
        }
        return ResponseEntity.ok().body(Map.of("status", "error", "message",
                "No se ha encontrado el ID del paciente"));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> eliminarComprobante(@PathVariable Long id){
        Optional<Comprobante> o = compService.buscarPorIdComprobante(id);
        if(o.isPresent()) {
            compService.eliminarComprobante(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
