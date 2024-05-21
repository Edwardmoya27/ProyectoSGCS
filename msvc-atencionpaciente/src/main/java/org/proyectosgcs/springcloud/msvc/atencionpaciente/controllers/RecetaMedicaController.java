package org.proyectosgcs.springcloud.msvc.atencionpaciente.controllers;

import feign.FeignException;
import jakarta.annotation.security.RolesAllowed;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.Auth.JwtTokenService;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.clients.MedicamentoClientRest;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.clients.MedicoClientRest;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.clients.PacienteClientRest;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Medicamento;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Medico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Paciente;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.Receta;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.services.DiagnosticoService;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.services.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recetas")
public class RecetaMedicaController {

    @Autowired
    private RecetaService recetaService;
    @Autowired
    private MedicoClientRest medicoClientRest;
    @Autowired
    private PacienteClientRest pacienteClientRest;
    @Autowired
    private MedicamentoClientRest medicamentoClientRest;
    @Autowired
    private DiagnosticoService diagnosticoMedicoService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @RolesAllowed({"ADMIN"})
    @GetMapping
    public ResponseEntity<?> getAllRecetas() {
        return ResponseEntity.status(HttpStatus.OK).body(recetaService.findAll());
    }

    @RolesAllowed({"ADMIN", "MEDICO", "PACIENTE"})
    @GetMapping("/{idReceta}")
    public ResponseEntity<?> getRecetaById(@PathVariable Long idReceta) {
        Optional<Receta> recetaOptional = recetaService.findById(idReceta);
        if(recetaOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "Receta no encontrada")
            );
        return ResponseEntity.status(HttpStatus.OK).body(recetaOptional.get());
    }

    @RolesAllowed({"ADMIN","MEDICO"})
    @PostMapping
    public ResponseEntity<?> crearReceta(
            @RequestBody Receta receta,
            @RequestHeader("Authorization") String token) {

        jwtTokenService.setToken(token.replace("Bearer ", ""));
        try {
            Optional<Medico> medicoOptional = medicoClientRest.obtenerMedicoPorId(receta.getIdMedico());
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
            Optional<Paciente> pacienteOptional = pacienteClientRest.obtenerPacientePorId(receta.getIdPaciente());
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


        try {
            Optional<Medicamento> medicamentoOptional = medicamentoClientRest.obtenerMedicamentoPorId(receta.getIdMedicamento());
            if (medicamentoOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "status", "error",
                                "message", "Medicamento no encontrado"
                        )
                );
        } catch (FeignException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Error al conectar con el microservicio Gestión Medicamento", "data", exception.getMessage())
            );
        }

        jwtTokenService.clearToken();

        return ResponseEntity.status(HttpStatus.CREATED).body(recetaService.saveReceta(receta));

    }

    @RolesAllowed({"ADMIN","MEDICO"})
    @PutMapping("/{idReceta}")
    public ResponseEntity<?> editarReceta(
            @PathVariable Long idReceta,
            @RequestBody Receta receta,
            @RequestHeader("Authorization") String token) {

        Optional<Receta> recetaOptional = recetaService.findById(idReceta);
        if (recetaOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Receta no encontrada")
            );

        jwtTokenService.setToken(token.replace("Bearer ", ""));
        try {
            Optional<Medico> medicoOptional = medicoClientRest.obtenerMedicoPorId(receta.getIdMedico());
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
            Optional<Paciente> pacienteOptional = pacienteClientRest.obtenerPacientePorId(receta.getIdPaciente());
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


        try {
            Optional<Medicamento> medicamentoOptional = medicamentoClientRest.obtenerMedicamentoPorId(receta.getIdMedicamento());
            if (medicamentoOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "status", "error",
                                "message", "Medicamento no encontrado"
                        )
                );
        } catch (FeignException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Error al conectar con el microservicio Gestión Medicamento", "data", exception.getMessage())
            );
        }

        jwtTokenService.clearToken();

        Receta recetaDB = recetaOptional.get();
        receta.setId(idReceta);
        recetaDB = receta;
        return ResponseEntity.status(HttpStatus.CREATED).body(recetaService.saveReceta(recetaDB));

    }

    @RolesAllowed({"ADMIN","MEDICO"})
    @DeleteMapping("/{idReceta}")
    public ResponseEntity<?> eliminarReceta(@PathVariable Long idReceta) {
        Optional<Receta> recetaOptional = recetaService.findById(idReceta);
        if (recetaOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Receta no encontrada")
            );
        recetaService.deleteRecetaMedica(idReceta);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                Map.of("status", "ok","message", "La receta se ha eliminado correctamente")
        );
    }

}
