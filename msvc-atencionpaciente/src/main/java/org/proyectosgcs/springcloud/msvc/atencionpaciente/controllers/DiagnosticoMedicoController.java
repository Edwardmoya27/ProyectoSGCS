package org.proyectosgcs.springcloud.msvc.atencionpaciente.controllers;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.clients.MedicoClientRest;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.clients.PacienteClientRest;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.config.JwtFeignInterceptor;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Medico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Paciente;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.DiagnosticoMedico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.services.DiagnosticoMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/diagnosticos")
public class DiagnosticoMedicoController {

    @Autowired
    private DiagnosticoMedicoService diagnosticoMedicoService;
    @Autowired
    private MedicoClientRest medicoClientRest;
    @Autowired
    private PacienteClientRest pacienteClientRest;


    @GetMapping
    public ResponseEntity<List<DiagnosticoMedico>> listarDiagnosticos() {
        List<DiagnosticoMedico> diagnosticos = diagnosticoMedicoService.findAll();
        return ResponseEntity.ok(diagnosticos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticoMedico> listarDiagnoticoPorID(@PathVariable Long id) {
        return diagnosticoMedicoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearDiagnostico(
            @RequestBody DiagnosticoMedico diagnosticoMedico,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        //Obtener Token y pasarlo al microservicio
        String token = authorizationHeader.replace("Bearer ", "");
        JwtFeignInterceptor.setToken(token);

        //Verificar si existe un medico a travez de otro microservicio
        Optional<Medico> medicoOptional = medicoClientRest.obtenerMedicoPorId(diagnosticoMedico.getIdMedico());
        if (medicoOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "No existe el médico"));

        //Verificar si existe un medico a travez de otro microservicio
        Optional<Paciente> pacienteOptional = pacienteClientRest.obtenerPacientePorId(diagnosticoMedico.getIdPaciente());
        if (pacienteOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "No existe el paciente"));


        DiagnosticoMedico savedDiagnostico = diagnosticoMedicoService.saveDiagnosticoMedico(diagnosticoMedico);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDiagnostico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticoMedico> EditarDiagnostico(@PathVariable Long id, @RequestBody DiagnosticoMedico diagnosticoMedico) {
        return diagnosticoMedicoService.findById(id)
                .map(existingDiagnostico -> {
                    diagnosticoMedico.setId(id);
                    // Guarda el diagnóstico médico actualizado
                    DiagnosticoMedico updatedDiagnostico = diagnosticoMedicoService.saveDiagnosticoMedico(diagnosticoMedico);
                    // Retorna el diagnóstico médico actualizado con el estado HTTP OK
                    return new ResponseEntity<>(updatedDiagnostico, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Retorna NOT_FOUND si no existe el diagnóstico
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDiagnostico(@PathVariable Long id) {
        diagnosticoMedicoService.deleteDiagnosticoMedico(id);
        return ResponseEntity.ok().build();
    }


    //MetodosRemotos

    @GetMapping("/pacientes/{id}")

    public ResponseEntity<?> obtenerListaDiagnosticosPorPacienteId(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){

        String token = authorizationHeader.replace("Bearer ", "");
        JwtFeignInterceptor.setToken(token);

        Optional<Paciente> pacienteOptional = pacienteClientRest.obtenerPacientePorId(id);
        if (pacienteOptional.isEmpty())
            return  ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "El paciente con el ID " +id + " no se encuentra")
            );
        Paciente pacienteApi = pacienteOptional.get();
        List<DiagnosticoMedico> diagnosticoMedicos = diagnosticoMedicoService.findByPacienteId(pacienteApi.getId());
        return ResponseEntity.ok(diagnosticoMedicos);
    }
}

