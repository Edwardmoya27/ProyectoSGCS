package org.proyectosgcs.springcloud.msvc.pago.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.proyectosgcs.springcloud.msvc.pago.Auth.JwtAuthorizationHelper;
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
    private JwtAuthorizationHelper jwtAuthorizationHelper;

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<?> listarComprobantes(HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        return ResponseEntity.ok(compService.listarComprobantes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorIdComprobante(@PathVariable Long id, HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN") &&
                !jwtAuthorizationHelper.validarRol(request, "MEDICO") &&
                !jwtAuthorizationHelper.validarRol(request, "PACIENTE")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        Optional<Comprobante> PacienteOptional = compService.buscarPorIdComprobante(id);
        if(PacienteOptional.isPresent()) {
            return ResponseEntity.ok(PacienteOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    //otros metodos

    @PostMapping("/generar")
    public ResponseEntity<?> generarComprobante(@RequestBody Comprobante comprobante, HttpServletRequest request) {

        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        Optional<Pago> pagoOptional = pagoService.buscarPorIdPago(comprobante.getPago().getId());

        if(pagoOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "No pago no existe"
            ));

        return ResponseEntity.status(HttpStatus.CREATED).body(compService.generarComprobante(comprobante));

    }

}
