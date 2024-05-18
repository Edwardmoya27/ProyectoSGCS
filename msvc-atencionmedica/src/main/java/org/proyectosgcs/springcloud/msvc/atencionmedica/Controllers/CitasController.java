package org.proyectosgcs.springcloud.msvc.atencionmedica.Controllers;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Auth.JwtAuthorizationHelper;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Clients.MedicoClientRest;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Clients.PagoClientRest;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Cita;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Paciente;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Medico;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Pago;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Services.CitasService;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Services.PacienteService;
import org.proyectosgcs.springcloud.msvc.atencionmedica.config.JwtFeignInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @file: CitaController
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 05:20 p. m.
 */
@RestController
@RequestMapping("api/citas")
public class CitasController {
    @Autowired
    private CitasService citasService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    MedicoClientRest medicoClientRest;
    @Autowired
    private PagoClientRest pagoClientRest;

    @Autowired
    private JwtAuthorizationHelper jwtAuthorizationHelper;

    @GetMapping
    public List<Cita> listarCitas(){
        return citasService.listarCitas();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorCita(@PathVariable Long id){
        Optional<Cita> CitasOptional = citasService.buscarPorIdCitas(id);
        if(CitasOptional.isPresent()) {
            return ResponseEntity.ok(CitasOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<?> crearCita(
            @RequestBody Cita cita,
            HttpServletRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){

        //Verificar token de acceso con rol ADMIN
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

        //Enviar el token a otros microservicios
        String token = authorizationHeader.replace("Bearer ", "");
        JwtFeignInterceptor.setToken(token);

        //Validar si existe el medico en el microservicio GESTION MEDICO
        Optional<Medico> medicoOptional = medicoClientRest.obtenerMedicoPorId(cita.getMedicoId());
        if (medicoOptional.isEmpty())
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", "error",
                            "message", "El médico no existe"
                    )
            );

        //Validar si existe el paciente
        Optional<Paciente> pacienteOptional = pacienteService.buscarPorIdPaciente(cita.getPacienteId());
        if (pacienteOptional.isEmpty())
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", "error",
                            "message", "El paciente no existe"
                    )
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(citasService.guardarCitas(cita));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCita(@PathVariable Long id,@RequestBody Cita cita, HttpServletRequest request){
        //Verificar token de acceso con rol ADMIN
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

        Optional<Cita> o = citasService.buscarPorIdCitas(id);
        if(o.isPresent()) {
            Cita citaDB = o.get();
            citaDB.setFechaHora(cita.getFechaHora());
            citaDB.setEstado(cita.getEstado());
            citaDB.setPacienteId(cita.getPacienteId());
            citaDB.setMedicoId(cita.getMedicoId());
            citaDB.setMotivo(cita.getMotivo());
            citaDB.setPagoId(cita.getPagoId());
            return ResponseEntity.status(HttpStatus.CREATED).body(citasService.guardarCitas(citaDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> eliminarCitas(@PathVariable Long id, HttpServletRequest request){

        //Verificar token de acceso con rol ADMIN
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

        Optional<Cita> o = citasService.buscarPorIdCitas(id);
        if(o.isPresent()) {
            citasService.eliminarCitas(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
        }

    //metodos Remotos
    @PostMapping("/{citaId}/asignar-pago")
    public ResponseEntity<?> asignarPago(@RequestBody Map<String, Long> data,
                                         @PathVariable Long citaId,
                                         HttpServletRequest request,
                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){

        //Verificar token de acceso con rol ADMIN
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

        //Enviar el token a otros microservicios
        String token = authorizationHeader.replace("Bearer ", "");
        JwtFeignInterceptor.setToken(token);

        Optional<Cita> citaOptional = citasService.buscarPorIdCitas(citaId);
        if(citaOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of(
                    "status","error", "message", "No existe la cita"));

        Optional<Pago> pagoOptional = pagoClientRest.obtenerPagoPorId(data.get("pagoId"));
        if (pagoOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of(
                    "status","error", "message", "El pago no existe"));

        Pago pagoDB = pagoOptional.get();
        Cita citaDB = citaOptional.get();
        citaDB.setPagoId(pagoDB.getId());
        return ResponseEntity.ok().body(citasService.guardarCitas(citaDB));

    }

    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<?> listarCitasPorIdMedico(@PathVariable Long idMedico, HttpServletRequest request){

        //Verificar token de acceso con rol ADMIN
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN") &&
                !jwtAuthorizationHelper.validarRol(request, "MEDICO")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

        Optional<Medico> medicoOptional = medicoClientRest.obtenerMedicoPorId(idMedico);
        if (medicoOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of(
                    "status","error", "message", "El médico no existe"));

        return ResponseEntity.ok().body(citasService.obtenerCitasPorIdMedico(idMedico));

    }

}
