package org.proyectosgcs.springcloud.msvc.atencionpaciente.controllers;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.modules.entity.DiagnosticoMedico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.services.DiagnosticoMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnosticos")
public class DiagnosticoMedicoController {

    @Autowired
    private DiagnosticoMedicoService diagnosticoMedicoService;

    @GetMapping
    public ResponseEntity<List<DiagnosticoMedico>> getAllDiagnosticos() {
        List<DiagnosticoMedico> diagnosticos = diagnosticoMedicoService.findAll();
        return ResponseEntity.ok(diagnosticos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticoMedico> getDiagnosticoById(@PathVariable Long id) {
        return diagnosticoMedicoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<DiagnosticoMedico> createDiagnostico(@RequestBody DiagnosticoMedico diagnosticoMedico) {
        DiagnosticoMedico savedDiagnostico = diagnosticoMedicoService.saveDiagnosticoMedico(diagnosticoMedico);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDiagnostico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticoMedico> updateDiagnostico(@PathVariable Long id, @RequestBody DiagnosticoMedico diagnosticoMedico) {
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
    public ResponseEntity<Void> deleteDiagnostico(@PathVariable Long id) {
        diagnosticoMedicoService.deleteDiagnosticoMedico(id);
        return ResponseEntity.ok().build();
    }
}

