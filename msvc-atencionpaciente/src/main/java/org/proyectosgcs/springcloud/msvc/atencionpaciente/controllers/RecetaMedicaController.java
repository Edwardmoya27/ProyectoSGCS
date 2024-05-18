package org.proyectosgcs.springcloud.msvc.atencionpaciente.controllers;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.clients.MedicoClientRest;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.clients.PacienteClientRest;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.config.JwtFeignInterceptor;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Medico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.Paciente;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.DiagnosticoMedico;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.RecetaMedica;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.services.DiagnosticoMedicoService;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.services.RecetaMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recetas")
public class RecetaMedicaController {

    @Autowired
    private RecetaMedicaService recetaMedicaService;
    @Autowired
    private MedicoClientRest medicoClientRest;
    @Autowired
    private PacienteClientRest pacienteClientRest;
    @Autowired
    private DiagnosticoMedicoService diagnosticoMedicoService;

    @GetMapping
    public ResponseEntity<List<RecetaMedica>> getAllRecetas() {
        List<RecetaMedica> recetas = recetaMedicaService.findAll();
        return ResponseEntity.ok(recetas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaMedica> getRecetaById(@PathVariable Long id) {
        return recetaMedicaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createReceta(
            @RequestBody RecetaMedica recetaMedica,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        //Obtener Token y pasarlo al microservicio
        String token = authorizationHeader.replace("Bearer ", "");
        JwtFeignInterceptor.setToken(token);

        //Verificar si existe un medico a travez de otro microservicio
        Optional<Medico> medicoOptional = medicoClientRest.obtenerMedicoPorId(recetaMedica.getIdMedico());
        if (medicoOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "No existe el médico"));

        //Verificar si existe un medico a travez de otro microservicio
        Optional<Paciente> pacienteOptional = pacienteClientRest.obtenerPacientePorId(recetaMedica.getIdPaciente());
        if (pacienteOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "No existe el paciente"));

        //Verifica si existe el diagnostico medico
        Optional<DiagnosticoMedico> diagnosticoMedicoOptional = diagnosticoMedicoService.findById(recetaMedica.getDiagnosticoMedico().getId());
        if (diagnosticoMedicoOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "No existe el diagnostico médico"));
        RecetaMedica savedReceta = recetaMedicaService.saveRecetaMedica(recetaMedica);
        return ResponseEntity.ok(savedReceta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaMedica> updateReceta(@PathVariable Long id, @RequestBody RecetaMedica recetaMedica) {
        return recetaMedicaService.findById(id)
                .map(existingReceta -> {
                    recetaMedica.setId(id); // Asegúrate de que existe un método setId en tu clase RecetaMedica
                    return new ResponseEntity<>(recetaMedicaService.saveRecetaMedica(recetaMedica), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceta(@PathVariable Long id) {
        recetaMedicaService.deleteRecetaMedica(id);
        return ResponseEntity.ok().build();
    }

    //otros metodos

    @PostMapping("/crear-para-diagnostico/{idDiagnostico}")
    public ResponseEntity<RecetaMedica> crearRecetaParaDiagnostico(
            @PathVariable Long idDiagnostico,
            @RequestBody RecetaMedica recetaMedica) {
        RecetaMedica nuevaReceta = recetaMedicaService.crearRecetaParaDiagnostico(idDiagnostico, recetaMedica);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReceta);
    }
}
