package org.proyectosgcs.springcloud.msvc.atencionmedica.Controllers;

import jakarta.validation.Valid;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Paciente;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping
    public List<Paciente> listarPacientes(){
        return pacienteService.listarPacientes();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorIdPaciente(@PathVariable Long id){
        Optional<Paciente> PacienteOptional = pacienteService.buscarPorIdPaciente(id);
        if(PacienteOptional.isPresent()) {
            return ResponseEntity.ok(PacienteOptional.get());
        }
        return ResponseEntity.ok().body(Map.of("status", "error", "message",
                "No existe paciente con el ID " + id));
    }
    @PostMapping
    public ResponseEntity<?> guardarPaciente(@Valid @RequestBody Paciente paciente, BindingResult result){
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

    @PutMapping("/{id}")
    public ResponseEntity<?> editarPaciente(@PathVariable Long id,@RequestBody Paciente paciente){
        Optional<Paciente> o = pacienteService.buscarPorIdPaciente(id);
        if(o.isPresent()) {
            Paciente pacienteDB = o.get();
            pacienteDB.setNombres(paciente.getNombres());
            pacienteDB.setApellidos(paciente.getApellidos());
            pacienteDB.setFechaNacimiento(paciente.getFechaNacimiento());
            pacienteDB.setGenero(paciente.getGenero());
            pacienteDB.setDireccion(paciente.getDireccion());
            pacienteDB.setTelefono(paciente.getTelefono());
            pacienteDB.setEmail(paciente.getEmail());
            pacienteDB.setSeguroMedico(paciente.getSeguroMedico());
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.guardarPaciente(pacienteDB));
        }
        return ResponseEntity.ok().body(Map.of("status", "error", "message",
                "No existe paciente con el ID " + id));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id){
        Optional<Paciente> o = pacienteService.buscarPorIdPaciente(id);
        if(o.isPresent()) {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok().body(Map.of("status", "ok", "message",
                    "Se ha eliminado correctamente el paciente"));
        }
        return ResponseEntity.ok().body(Map.of("status", "error", "message",
                "No existe paciente con el ID " + id));
    }
}
