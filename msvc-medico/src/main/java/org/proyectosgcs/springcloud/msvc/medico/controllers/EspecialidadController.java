package org.proyectosgcs.springcloud.msvc.medico.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import org.proyectosgcs.springcloud.msvc.medico.Auth.JwtAuthorizationHelper;
import org.proyectosgcs.springcloud.msvc.medico.models.entity.Especialidad;
import org.proyectosgcs.springcloud.msvc.medico.models.entity.Medico;
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
    private EspecialidadService especialidadService;

    @RolesAllowed({"ADMIN"})
    @GetMapping
    public ResponseEntity<?> listar(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(especialidadService.listarEspecialidades());
    }

    @RolesAllowed({"ADMIN"})
    @GetMapping("/{idEspecialidad}")
    public ResponseEntity<?> obtenerEspecialidadPorId(@PathVariable Long idEspecialidad){
        Optional<Especialidad> especialidadOptional = especialidadService.obtenerEspecialidad(idEspecialidad);
        if (especialidadOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "Especialidad no encontrada")
            );
        return ResponseEntity.status(HttpStatus.OK).body(especialidadOptional.get());
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping
    public ResponseEntity<?> crearEspecialidad(@RequestBody Especialidad especialidad){
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadService.registrarEspecialidad(especialidad));
    }

    @RolesAllowed({"ADMIN"})
    @PutMapping("/{idEspecialidad}")
    public ResponseEntity <?> editarEspecialidad(@RequestBody Especialidad especialidad, @PathVariable Long idEspecialidad) {
        Optional<Especialidad> especialidadOptional = especialidadService.obtenerEspecialidad(idEspecialidad);
        if(especialidadOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "Especialidad no encontrada")
            );
        Especialidad especialidadDB = especialidadOptional.get();
        especialidad.setId(idEspecialidad);
        especialidadDB = especialidad;
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadService.registrarEspecialidad(especialidadDB));
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping("/{idEspecialidad}")
    public ResponseEntity<?> eliminarEspecialidad(@PathVariable Long idEspecialidad, HttpServletRequest request){
        Optional<Especialidad> especialidadOptional = especialidadService.obtenerEspecialidad(idEspecialidad);
        if(especialidadOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "Especialidad no encontrada")
            );
        especialidadService.eliminarEspecialidad(idEspecialidad);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                Map.of("status", "ok","message", "La especialidad se ha eliminado correctamente")
        );
    }

}
