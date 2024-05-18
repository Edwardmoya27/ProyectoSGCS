package org.proyectosgcs.springcloud.msvc.pago.Controllers;
import jakarta.servlet.http.HttpServletRequest;
import org.proyectosgcs.springcloud.msvc.pago.Auth.JwtAuthorizationHelper;
import org.proyectosgcs.springcloud.msvc.pago.Services.PagoService;

import org.proyectosgcs.springcloud.msvc.pago.clients.CitaClientRest;
import org.proyectosgcs.springcloud.msvc.pago.clients.PacienteClientRest;
import org.proyectosgcs.springcloud.msvc.pago.config.JwtFeignInterceptor;
import org.proyectosgcs.springcloud.msvc.pago.models.Cita;
import org.proyectosgcs.springcloud.msvc.pago.models.Paciente;
import org.proyectosgcs.springcloud.msvc.pago.models.entity.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private JwtAuthorizationHelper jwtAuthorizationHelper;
    @Autowired
    private PacienteClientRest pacienteClientRest;
    @Autowired
    private CitaClientRest citaClientRest;

    @GetMapping
    public ResponseEntity<?> listarPagos(HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        return ResponseEntity.ok(pagoService.listarPagos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPagoPorId(@PathVariable Long id, HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        Optional<Pago> PagoOptional = pagoService.buscarPorIdPago(id);
        if(PagoOptional.isPresent()) {
            return ResponseEntity.ok(PagoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<?> guardarPago(@RequestBody Pago pago, HttpServletRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

        String token = authorizationHeader.replace("Bearer ", "");
        JwtFeignInterceptor.setToken(token);

        Optional<Paciente> pacienteOptional = pacienteClientRest.obtenerPacientePorId(pago.getPacienteId());

        if(pacienteOptional.isEmpty())
         return ResponseEntity.badRequest().body(Map.of(
                 "status", "error",
                 "message", "No existe el paciente"
         ));

        Optional<Cita> citaOptional = citaClientRest.obtenerCitaPorId(pago.getCitaId());
        if(citaOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "No existe la cita"
            ));

        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.guardarPago(pago));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarPago(@PathVariable Long id,@RequestBody Pago pago, HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        Optional<Pago> o = pagoService.buscarPorIdPago(id);
        if(o.isPresent()) {
            Pago pagoDB = o.get();
            pagoDB.setPacienteId(pago.getPacienteId());
            pagoDB.setCitaId(pago.getCitaId());
            pagoDB.setMonto(pago.getMonto());
            pagoDB.setFechaHora(pago.getFechaHora());
            pagoDB.setMetodoPago(pago.getMetodoPago());
            return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.guardarPago(pagoDB));
        }
        return ResponseEntity.ok().body(Map.of("status", "error", "message",
                "No se ha encontrado el ID del paciente"));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> eliminarPago(@PathVariable Long id, HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        Optional<Pago> o = pagoService.buscarPorIdPago(id);
        if(o.isPresent()) {
            pagoService.eliminarPago(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
