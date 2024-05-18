package org.proyectosgcs.springcloud.msvc.medico.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.proyectosgcs.springcloud.msvc.medico.Auth.JwtAuthorizationHelper;
import org.proyectosgcs.springcloud.msvc.medico.models.entity.Especialidad;
import org.proyectosgcs.springcloud.msvc.medico.services.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService service;
    @Autowired
    private JwtAuthorizationHelper jwtAuthorizationHelper;

    @GetMapping
    public ResponseEntity<?> listar(HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        return ResponseEntity.ok(service.listarEspecialidades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id, HttpServletRequest request){

        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

        Optional<Especialidad> especialidadOptional = service.obtenerEspecialidad(id);
        if (especialidadOptional.isPresent()){
            return ResponseEntity.ok(especialidadOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear (@RequestBody Especialidad especialidad, HttpServletRequest request){

        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarEspecialidad(especialidad));
    }

    @PutMapping("/{id}")
    public ResponseEntity <? > editar (@RequestBody Especialidad especialidad, @PathVariable Long id, HttpServletRequest request) {
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        Optional<Especialidad> op = service.obtenerEspecialidad(id);
        if (op.isPresent()) {
            Especialidad especialidadDb = op.get();
            especialidadDb.setNombre(especialidad.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarEspecialidad(especialidad));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        Optional<Especialidad> op = service.obtenerEspecialidad(id);
        if (op.isPresent()){
            service.eliminarEspecialidad(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
