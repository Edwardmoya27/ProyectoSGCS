package org.proyectosgcs.springcloud.msvc.medico.controllers;

import org.proyectosgcs.springcloud.msvc.medico.models.entity.Especialidad;
import org.proyectosgcs.springcloud.msvc.medico.models.entity.Medico;
import org.proyectosgcs.springcloud.msvc.medico.services.EspecialidadService;
import org.proyectosgcs.springcloud.msvc.medico.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;
    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    public List<Medico> listar(){
        return service.listarMedicos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Medico> medicoOptional = service.obtenerMedico(id);
        if (medicoOptional.isPresent()){
            return ResponseEntity.ok(medicoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear (@RequestBody Medico medico){
        Optional<Especialidad> optionalEspecialidad = especialidadService.obtenerEspecialidad(medico.getEspecialidad().getId());
        if (optionalEspecialidad.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarMedico(medico));
        } else {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("error", "La especialidad no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity <? > editar (@RequestBody Medico medico, @PathVariable Long id) {
        Optional<Medico> op = service.obtenerMedico(id);
        if (op.isPresent()) {
            Medico medicoDb = op.get();
            medicoDb.setNombres(medico.getNombres());
            medicoDb.setApellidos(medico.getApellidos());
            medicoDb.setFechaNacimiento(medico.getFechaNacimiento());
            medicoDb.setEspecialidad(medico.getEspecialidad());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarMedico(medicoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Medico> op = service.obtenerMedico(id);
        if (op.isPresent()){
            service.eliminarMedico(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
