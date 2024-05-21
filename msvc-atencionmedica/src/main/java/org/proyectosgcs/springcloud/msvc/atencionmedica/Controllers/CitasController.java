package org.proyectosgcs.springcloud.msvc.atencionmedica.Controllers;

import feign.FeignException;
import jakarta.annotation.security.RolesAllowed;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Auth.JwtTokenService;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Clients.MedicoClientRest;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Clients.PagoClientRest;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Cita;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Paciente;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Medico;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Pago;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Services.CitasService;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private CitasService citaService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private MedicoClientRest medicoClientRest;
    @Autowired
    private PagoClientRest pagoClientRest;
    @Autowired
    private JwtTokenService jwtTokenService;

    @RolesAllowed({"ADMIN"})
    @GetMapping
    public ResponseEntity<?> listarCitas() {
        return ResponseEntity.status(HttpStatus.OK).body(citaService.listarCitas());
    }

    @RolesAllowed({"ADMIN", "MEDICO", "PACIENTE"})
    @GetMapping("/{idCita}")
    public ResponseEntity<?> obtenerCitaPorID(@PathVariable Long idCita) {
        Optional<Cita> citaOptional = citaService.buscarPorIdCitas(idCita);
        if (citaOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Cita no encontrada")
            );
        return ResponseEntity.status(HttpStatus.OK).body(citaOptional.get());
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping
    public ResponseEntity<?> crearCita(@RequestBody Cita cita, @RequestHeader("Authorization") String token) {
        try {
            jwtTokenService.setToken(token.replace("Bearer ", ""));
            Optional<Medico> medicoOptional = medicoClientRest.obtenerMedicoPorId(cita.getMedicoId());
            jwtTokenService.clearToken();
            if (medicoOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "status", "error",
                                "message", "Médico no encontrado"
                        )
                );
        } catch (FeignException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Error al conectar con el microservicio Gestión Medicos", "data", exception.getMessage())
            );
        }

        Optional<Paciente> pacienteOptional = pacienteService.buscarPorIdPaciente(cita.getPacienteId());

        if (pacienteOptional.isEmpty())
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", "error",
                            "message", "Paciente no encontrado"
                    )
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(citaService.guardarCitas(cita));

    }

    @RolesAllowed({"ADMIN"})
    @PutMapping("/{idCita}")
    public ResponseEntity<?> editarCita(@PathVariable Long idCita,@RequestBody Cita cita, @RequestHeader("Authorization") String token){
        Optional<Cita> citaOptional = citaService.buscarPorIdCitas(idCita);
        if(citaOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "Cita no encontrada")
            );

        try {
            jwtTokenService.setToken(token.replace("Bearer ", ""));
            Optional<Medico> medicoOptional = medicoClientRest.obtenerMedicoPorId(cita.getMedicoId());
            jwtTokenService.clearToken();
            if (medicoOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "status", "error",
                                "message", "Médico no encontrado"
                        )
                );
        }catch (FeignException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "Error al conectar con el microservicio Gestión Medicos", "data", exception.getMessage())
            );
        }

        Optional<Paciente> pacienteOptional = pacienteService.buscarPorIdPaciente(cita.getPacienteId());

        if (pacienteOptional.isEmpty())
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", "error",
                            "message", "Paciente no encontrado"
                    )
            );

        Cita citaDB = citaOptional.get();
        cita.setId(idCita);
        citaDB = cita;
        return ResponseEntity.status(HttpStatus.CREATED).body(citaService.guardarCitas(citaDB));
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping ("/{idCita}")
    public ResponseEntity<?> eliminarCita(@PathVariable Long idCita){
        Optional<Cita> citaOptional = citaService.buscarPorIdCitas(idCita);
        if(citaOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "Cita no encontrada")
            );
        citaService.eliminarCitas(idCita);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                Map.of("status", "ok","message", "La cita se ha eliminado correctamente")
        );
    }

    @RolesAllowed({"ADMIN", "MEDICO"})
    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<?> listarCitasPorIdMedico(@PathVariable Long idMedico){
        return ResponseEntity.status(HttpStatus.OK).body(citaService.obtenerCitasPorIdMedico(idMedico));
    }

    @RolesAllowed({"ADMIN", "PACIENTE"})
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<?> listarCitasPorIdPaciente(@PathVariable Long idPaciente){
        return ResponseEntity.status(HttpStatus.OK).body(citaService.obtenerCitasPorIdPaciente(idPaciente));
    }


    @RolesAllowed({"ADMIN"})
    @PostMapping("/{idCita}/asignar-pago")
    public ResponseEntity<?> asignarPago(
            @RequestBody Map<String, Long> data,
            @PathVariable Long idCita,
            @RequestHeader("Authorization") String token){

        Optional<Cita> citaOptional = citaService.buscarPorIdCitas(idCita);
        if(citaOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "Cita no encontrada")
            );

        try {
            jwtTokenService.setToken(token.replace("Bearer ", ""));
            Optional<Pago> pagoOptional = pagoClientRest.obtenerPagoPorId(data.get("pagoId"));
            jwtTokenService.clearToken();
            if (pagoOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "status", "error",
                                "message", "Pago no encontrado"
                        )
                );
        }catch (FeignException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "Error al conectar con el microservicio Gestión Pagos", "data", exception.getMessage())
            );
        }
        Cita citaDB = citaOptional.get();
        citaDB.setPagoId(data.get("pagoId"));
        return ResponseEntity.status(HttpStatus.OK).body(citaService.guardarCitas(citaDB));
    }

}
