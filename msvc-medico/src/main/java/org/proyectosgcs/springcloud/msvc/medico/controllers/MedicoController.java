package org.proyectosgcs.springcloud.msvc.medico.controllers;

import feign.FeignException;
import jakarta.annotation.security.RolesAllowed;
import org.proyectosgcs.springcloud.msvc.medico.Auth.JwtTokenService;
import org.proyectosgcs.springcloud.msvc.medico.Clients.CitaClientRest;
import org.proyectosgcs.springcloud.msvc.medico.models.Cita;
import org.proyectosgcs.springcloud.msvc.medico.models.entity.Especialidad;
import org.proyectosgcs.springcloud.msvc.medico.models.entity.Medico;
import org.proyectosgcs.springcloud.msvc.medico.services.EspecialidadService;
import org.proyectosgcs.springcloud.msvc.medico.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private EspecialidadService especialidadService;
    @Autowired
    private CitaClientRest citaClientRest;
    @Autowired
    private JwtTokenService jwtTokenService;

    @RolesAllowed({"ADMIN"})
    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok().body(medicoService.listarMedicos());
    }

    @RolesAllowed({"ADMIN","MEDICO","PACIENTE"})
    @GetMapping("/{idMedico}")
    public ResponseEntity<?> obtenerMedicoPorId(@PathVariable Long idMedico){
        Optional<Medico> medicoOptional = medicoService.obtenerMedicoPorId(idMedico);
        if (medicoOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "Médico no encontrado")
            );
        return ResponseEntity.status(HttpStatus.OK).body(medicoOptional.get());
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping
    public ResponseEntity<?> crearMedico(@RequestBody Medico medico){
        Optional<Especialidad> especialidadOptional = especialidadService.obtenerEspecialidad(medico.getEspecialidad().getId());
        if(especialidadOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "La especialidad no existe. Por favor vuelva a verificar")
            );
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.registrarMedico(medico));
    }

    @RolesAllowed({"ADMIN","MEDICO"})
    @PutMapping("/{idMedico}")
    public ResponseEntity<?> editarMedico(@RequestBody Medico medico, @PathVariable Long idMedico) {
        Optional<Medico> medicoOptional = medicoService.obtenerMedicoPorId(idMedico);
        if(medicoOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "El médico no existe")
            );
        Medico medicoDB = medicoOptional.get();
        medico.setId(idMedico);
        medicoDB = medico;
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.registrarMedico(medicoDB));
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping("/{idMedico}")
    public ResponseEntity<?> eliminarMedico(@PathVariable Long idMedico){
        Optional<Medico> medicoOptional = medicoService.obtenerMedicoPorId(idMedico);
        if(medicoOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "El médico no existe")
            );
        medicoService.eliminarMedico(idMedico);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                Map.of("status", "ok","message", "El médico se ha eliminado correctamente")
        );
    }

    //Metodos remotos

    //Listado de citas de un medico ID

    @RolesAllowed({"ADMIN", "MEDICO"})
    @GetMapping("/{idMedico}/citas")
    public ResponseEntity<?> obtenerCitasIdMedico(@PathVariable Long idMedico, @RequestHeader("Authorization") String token){
        Optional<Medico> medicoOptional = medicoService.obtenerMedicoPorId(idMedico);
        if(medicoOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "El médico no existe")
            );

        try {
            jwtTokenService.setToken(token.replace("Bearer ", ""));
            List<Cita> listadoCitasIdMedicoApi = citaClientRest.obtenerCitasPorIdMedico(idMedico);
            jwtTokenService.clearToken();
            return ResponseEntity.ok().body(listadoCitasIdMedicoApi);
        }catch (FeignException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error","message", "Error al conectar con el microservicio Gestion de Citas", "data", exception.getMessage())
            );
        }
    }

}
