package org.proyectosgcs.springcloud.msvc.medico.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.proyectosgcs.springcloud.msvc.medico.Auth.JwtAuthorizationHelper;
import org.proyectosgcs.springcloud.msvc.medico.Clients.CitaClientRest;
import org.proyectosgcs.springcloud.msvc.medico.Clients.MedicamentoClientRest;
import org.proyectosgcs.springcloud.msvc.medico.Clients.PacienteClientRest;
import org.proyectosgcs.springcloud.msvc.medico.models.Cita;
import org.proyectosgcs.springcloud.msvc.medico.models.Medicamento;
import org.proyectosgcs.springcloud.msvc.medico.models.Paciente;
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
    @Autowired
    private PacienteClientRest pacienteClientRest;
    @Autowired
    private CitaClientRest citaClientRest;
    @Autowired
    private MedicamentoClientRest medicamentoClientRest;

    @Autowired
    private JwtAuthorizationHelper jwtAuthorizationHelper;

    @GetMapping
    public ResponseEntity<?> listar(HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRolAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        return ResponseEntity.ok().body(service.listarMedicos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id, HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRolAdmin(request) || !jwtAuthorizationHelper.validarRolMedico(request) || !jwtAuthorizationHelper.validarRolPaciente(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        Optional<Medico> medicoOptional = service.obtenerMedico(id);
        if(medicoOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status", "error",
                    "message", "El médico con el ID " + id + " no existe"
            ));
        return ResponseEntity.ok(medicoOptional.get());
    }

    @PostMapping
    public ResponseEntity<?> crear (@RequestBody Medico medico, HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRolAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
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


    //Metodos remotos

    @GetMapping("/pacientes/{idPaciente}")
    public ResponseEntity<?> obtenerPaciente(@PathVariable Long idPaciente){
        Paciente pacienteApi = pacienteClientRest.obtenerPacientePorId(idPaciente);
        if (pacienteApi.getId() == null){
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("error", "No existe el paciente con el ID "+idPaciente);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
        Map<String, String> response = new HashMap<>();
        response.put("nombre", pacienteApi.getNombres() + " " + pacienteApi.getApellidos());
        return ResponseEntity.ok().body(response);
    }

    //Listado de citas de un medico ID
    @GetMapping("/{idMedico}/citas")
    public ResponseEntity<?> obtenerCitasIdMedico(@PathVariable Long idMedico){
        Optional<Medico> medicoOptional = service.obtenerMedico(idMedico);
        if(medicoOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("message","No existe el medico con el ID "+idMedico));
        List<Cita> listadoCitasIdMedicoApi = citaClientRest.obtenerCitasPorIdMedico(idMedico);
        if (listadoCitasIdMedicoApi.isEmpty()){
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("error", "No existen citas para el medico");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
        return ResponseEntity.ok().body(listadoCitasIdMedicoApi);
    }

    @GetMapping("/citas/{idCita}")
    public ResponseEntity<?> obtenerCitaIdPaciente(@PathVariable Long idCita){
        Cita citaApi = citaClientRest.obtenerCitaPorId(idCita);
        if (citaApi.getId() == null){
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("error", "No existe la cita con el ID "+idCita);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
        return ResponseEntity.ok().body(citaApi);
    }


    @GetMapping("/medicamentos")
    public ResponseEntity<?> obtenerLstadoMedicamentos(){
        try {
            List<Medicamento> medicamentoList = medicamentoClientRest.obtenerListadoMedicamentos();
            return ResponseEntity.ok().body(medicamentoList);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Hay un error en la conexion con el microservicio"));
        }
    }

}
