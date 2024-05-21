package org.proyectosgcs.springcloud.msvc.atencionpaciente.controllers;

import feign.FeignException;
import jakarta.annotation.security.RolesAllowed;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.Auth.JwtTokenService;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.clients.MedicoClientRest;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.clients.PacienteClientRest;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Medico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Paciente;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.Diagnostico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.services.DiagnosticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/diagnosticos")
public class DiagnosticoController {
    @Autowired
    private DiagnosticoService diagnosticoService;
    @Autowired
    private MedicoClientRest medicoClientRest;
    @Autowired
    private PacienteClientRest pacienteClientRest;
    @Autowired
    private JwtTokenService jwtTokenService;

    @RolesAllowed({"ADMIN"})
    @GetMapping
    public ResponseEntity<?> listarDiagnosticos() {
        return ResponseEntity.status(HttpStatus.OK).body(diagnosticoService.findAll());
    }

    @RolesAllowed({"ADMIN", "MEDICO", "PACIENTE"})
    @GetMapping("/{idDiagnostico}")
    public ResponseEntity<?> obtenerDiagnosticoPorID(@PathVariable Long idDiagnostico) {
        Optional<Diagnostico> diagnosticoOptional = diagnosticoService.findById(idDiagnostico);
        if(diagnosticoOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "Diagnostico no encontrado")
            );
        return ResponseEntity.status(HttpStatus.OK).body(diagnosticoOptional.get());
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping
    public ResponseEntity<?> crearDiagnostico(@RequestBody Diagnostico diagnostico, @RequestHeader("Authorization") String token) {
        jwtTokenService.setToken(token.replace("Bearer ", ""));
        try {
            Optional<Medico> medicoOptional = medicoClientRest.obtenerMedicoPorId(diagnostico.getIdMedico());
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

        try {
            Optional<Paciente> pacienteOptional = pacienteClientRest.obtenerPacientePorId(diagnostico.getIdPaciente());
            if (pacienteOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "status", "error",
                                "message", "Paciente no encontrado"
                        )
                );
        } catch (FeignException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Error al conectar con el microservicio Gestión Paciente", "data", exception.getMessage())
            );
        }

        jwtTokenService.clearToken();

        return ResponseEntity.status(HttpStatus.CREATED).body(diagnosticoService.saveDiagnostico(diagnostico));
    }

    @RolesAllowed({"ADMIN"})
    @PutMapping("/{idDiagnostico}")
    public ResponseEntity<?> editarDiagnostico(
            @PathVariable Long idDiagnostico,
            @RequestBody Diagnostico diagnostico,
            @RequestHeader("Authorization") String token) {

        Optional<Diagnostico> diagnosticoOptional = diagnosticoService.findById(idDiagnostico);
        if (diagnosticoOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Diagnostico no encontrado")
            );

        jwtTokenService.setToken(token.replace("Bearer ", ""));
        try {
            Optional<Medico> medicoOptional = medicoClientRest.obtenerMedicoPorId(diagnostico.getIdMedico());
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

        try {
            Optional<Paciente> pacienteOptional = pacienteClientRest.obtenerPacientePorId(diagnostico.getIdPaciente());
            if (pacienteOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "status", "error",
                                "message", "Paciente no encontrado"
                        )
                );
        } catch (FeignException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Error al conectar con el microservicio Gestión Paciente", "data", exception.getMessage())
            );
        }

        jwtTokenService.clearToken();

        Diagnostico diagnosticoDB = diagnosticoOptional.get();
        diagnostico.setId(idDiagnostico);
        diagnosticoDB = diagnostico;
        return ResponseEntity.status(HttpStatus.CREATED).body(diagnosticoService.saveDiagnostico(diagnosticoDB));
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping("/{idDiagnostico}")
    public ResponseEntity<?> eliminarDiagnostico(@PathVariable Long idDiagnostico) {
        Optional<Diagnostico> diagnosticoOptional = diagnosticoService.findById(idDiagnostico);
        if (diagnosticoOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Diagnostico no encontrado")
            );
        diagnosticoService.deleteDiagnosticoMedico(idDiagnostico);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                Map.of("status", "ok","message", "El diagnostico se ha eliminado correctamente")
        );
    }

    @RolesAllowed({"ADMIN", "PACIENTE"})
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<?> obtenerDiagnosticosPorPacienteId(
            @PathVariable Long idPaciente,
            @RequestHeader("Authorization") String token){
        jwtTokenService.setToken(token.replace("Bearer ", ""));
        try {
            Optional<Paciente> pacienteOptional = pacienteClientRest.obtenerPacientePorId(idPaciente);
            if (pacienteOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "status", "error",
                                "message", "Paciente no encontrado"
                        )
                );
        } catch (FeignException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Error al conectar con el microservicio Gestión Paciente", "data", exception.getMessage())
            );
        }
        jwtTokenService.clearToken();
        return ResponseEntity.status(HttpStatus.OK).body(diagnosticoService.findByIdPaciente(idPaciente));
    }

    @RolesAllowed({"ADMIN", "MEDICO"})
    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<?> obtenerDiagnosticosPorMedicoId(
            @PathVariable Long idMedico,
            @RequestHeader("Authorization") String token){
        jwtTokenService.setToken(token.replace("Bearer ", ""));
        try {
            Optional<Medico> medicoOptional = medicoClientRest.obtenerMedicoPorId(idMedico);
            if (medicoOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "status", "error",
                                "message", "Médico no encontrado"
                        )
                );
        } catch (FeignException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Error al conectar con el microservicio Gestión Médico", "data", exception.getMessage())
            );
        }
        jwtTokenService.clearToken();
        return ResponseEntity.status(HttpStatus.OK).body(diagnosticoService.findByIdMedico(idMedico));
    }

}

