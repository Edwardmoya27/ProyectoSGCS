package org.proyectosgcs.springcloud.msvc.atencionpaciente.controllers;

import org.proyectosgcs.springcloud.msvc.atencionpaciente.models.entity.RecetaMedica;
import org.proyectosgcs.springcloud.msvc.atencionpaciente.services.RecetaMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaMedicaController {

    @Autowired
    private RecetaMedicaService recetaMedicaService;

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

    @PostMapping("/")
    public ResponseEntity<RecetaMedica> createReceta(@RequestBody RecetaMedica recetaMedica) {
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
