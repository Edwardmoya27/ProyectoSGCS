package org.proyectosgcs.springcloud.msvc.atencionmedica.Controllers;

import feign.FeignException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Auth.JwtAuthorizationHelper;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Cita;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Paciente;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Services.CitasService;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @file: PacienteController
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 04:00 p. m.
 */
@RestController
@RequestMapping("api/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private CitasService citasService;
    @Autowired
    private JwtAuthorizationHelper jwtAuthorizationHelper;

    @RolesAllowed({"ADMIN"})
    @GetMapping
    public ResponseEntity<?> listarPacientes(){
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.listarPacientes());
    }

    @RolesAllowed({"ADMIN","MEDICO","PACIENTE"})
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPacientePorId(@PathVariable Long id){
        Optional<Paciente> pacienteOptional = pacienteService.buscarPorIdPaciente(id);
        if (pacienteOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Paciente no encontrado")
            );
        return ResponseEntity.status(HttpStatus.OK).body(pacienteOptional.get());
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping
    public ResponseEntity<?> crearPaciente(
            @Valid @RequestBody Paciente paciente,
            BindingResult result){
        try {
            if (result.hasErrors()) {
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(error -> {
                    errores.put(error.getField(), error.getDefaultMessage());
                });
                return ResponseEntity.badRequest().body(errores);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.guardarPaciente(paciente));
        } catch (DataIntegrityViolationException e) {
            Map<String, String> errores = new HashMap<>();
            errores.put("error", "Ya existe un paciente con el mismo DNI o email");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errores);
        }
    }

    @RolesAllowed({"ADMIN"})
    @PutMapping("/{idPaciente}")
    public ResponseEntity<?> editarPaciente(
            @PathVariable Long idPaciente,
            @RequestBody Paciente paciente){
        Optional<Paciente> pacienteOptional = pacienteService.buscarPorIdPaciente(idPaciente);
        if (pacienteOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Paciente no encontrado")
            );
        Paciente pacienteDB = pacienteOptional.get();
        paciente.setId(idPaciente);
        pacienteDB = paciente;
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.guardarPaciente(pacienteDB));
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping ("/{idPaciente}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long idPaciente){
        Optional<Paciente> pacienteOptional = pacienteService.buscarPorIdPaciente(idPaciente);
        if (pacienteOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Paciente no encontrado")
            );
        pacienteService.eliminarPaciente(idPaciente);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                Map.of("status", "ok","message", "El paciente se ha eliminado correctamente")
        );

    }

    @RolesAllowed({"ADMIN","PACIENTE"})
    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> obtenerPacientePorDNI(@PathVariable String dni) {
        Optional<Paciente> pacienteOptional = pacienteService.obtenerPacientePorDni(dni);
        if (pacienteOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Paciente no encontrado")
            );
        return ResponseEntity.status(HttpStatus.OK).body(pacienteOptional.get());
    }

    @RolesAllowed({"ADMIN","PACIENTE"})
    //otros metodos
    @GetMapping("/citas/{dni}")
    public ResponseEntity<?> obtenerCitasPorDNI(@PathVariable String dni) {
        Optional<Paciente> pacienteOptional = pacienteService.obtenerPacientePorDni(dni);
        if (pacienteOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "Paciente no encontrado")
            );
        List<Cita> citas = citasService.obtenerCitasPorIdPaciente(pacienteOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(citas);
    }
}
