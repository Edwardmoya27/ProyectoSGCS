package org.proyectosgcs.springcloud.msvc.medico.controllers;

import org.proyectosgcs.springcloud.msvc.medico.models.entity.Especialidad;
import org.proyectosgcs.springcloud.msvc.medico.services.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService service;

    @GetMapping
    public List<Especialidad> listar(){
        return service.listarEspecialidades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Especialidad> especialidadOptional = service.obtenerEspecialidad(id);
        if (especialidadOptional.isPresent()){
            return ResponseEntity.ok(especialidadOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear (@RequestBody Especialidad especialidad){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarEspecialidad(especialidad));
    }

    @PutMapping("/{id}")
    public ResponseEntity <? > editar (@RequestBody Especialidad especialidad, @PathVariable Long id) {
        Optional<Especialidad> op = service.obtenerEspecialidad(id);
        if (op.isPresent()) {
            Especialidad especialidadDb = op.get();
            especialidadDb.setNombre(especialidad.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarEspecialidad(especialidad));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Especialidad> op = service.obtenerEspecialidad(id);
        if (op.isPresent()){
            service.eliminarEspecialidad(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
