package org.proyectosgcs.springcloud.msvc.atencionmedica.Controllers;


import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.HistoriaMedica;

import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Paciente;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Services.HistoriaMedicaService;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @file: HistoriaMedicaController
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 05:21 p. m.
 */
@RestController
@RequestMapping("api/historiasmedicas")
public class HistoriaMedicaController {
    @Autowired
    private HistoriaMedicaService historiaMedicaService;
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<HistoriaMedica> listarHistorias(){
        return historiaMedicaService.listarHistorias();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorHistoria(@PathVariable Long id){
        Optional<HistoriaMedica> HistoriaOptional = historiaMedicaService.buscarPorIdHistoria(id);
        if(HistoriaOptional.isPresent()) {
            return ResponseEntity.ok(HistoriaOptional.get());
        }else{
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "No se encontraron datos para el ID de historia buscada");
            return ResponseEntity.ok().body(response);
        }
    }

    //HistoriaMedicaPorIdPaciente
    @GetMapping("/pacientes/{idPaciente}")
    public ResponseEntity<?> historiaMedicaPorIdPaciente(@PathVariable Long idPaciente){
        Optional<Paciente> pacienteOptional = pacienteService.buscarPorIdPaciente(idPaciente);
        if (pacienteOptional.isPresent()){
            Optional<HistoriaMedica> historiaMedicaOptional = historiaMedicaService.historiaMedicaPorIdPaciente(idPaciente);
            if (historiaMedicaOptional.isPresent()){
                Paciente pacienteDB = pacienteOptional.get();
                HistoriaMedica historiaMedicaDB = historiaMedicaOptional.get();
                Map<String, Object> pacienteNuevo = new LinkedHashMap<>();
                pacienteNuevo.put("paciente", pacienteDB.getNombres() + " " + pacienteDB.getApellidos());
                pacienteNuevo.put("alergias", historiaMedicaDB.getAlergias());
                pacienteNuevo.put("enfermedadesCronicas", historiaMedicaDB.getEnfermedadesCronicas());
                pacienteNuevo.put("cirugiasPrevias", historiaMedicaDB.getCirugiasPrevias());
                pacienteNuevo.put("hospitalizacionesPrevias", historiaMedicaDB.getHospitalizacionesPrevias());
                pacienteNuevo.put("medicamentosActuales", historiaMedicaDB.getMedicamentosActuales());
                pacienteNuevo.put("resultadosExamenesPrevios", historiaMedicaDB.getResultadosExamenesPrevios());
                return ResponseEntity.ok(pacienteNuevo);
            }else{
                return ResponseEntity.ok().body(Map.of("status", "error", "message",
                        "No existe historia medica del paciente con ID " + idPaciente));
            }
        }else{
            return ResponseEntity.ok().body(Map.of("status", "error", "message",
                    "No existe el paciente con ID " + idPaciente));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearHistoria(@RequestBody HistoriaMedica historiaMedica){
        try{
            Long idPaciente = historiaMedica.getPaciente().getId();
            Optional<Paciente> pacienteOptional = pacienteService.buscarPorIdPaciente(idPaciente);
            if (!pacienteOptional.isPresent())
                return ResponseEntity.ok().body(Map.of("status", "error", "message",
                        "No existe paciente con el ID " + idPaciente));
            return ResponseEntity.status(HttpStatus.CREATED).body(historiaMedicaService.guardarHistoria(historiaMedica));
        } catch (DataIntegrityViolationException e) {
            Map<String, String> errores = new HashMap<>();
            errores.put("error", "Ya existe una Historia Medica del paciente");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errores);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarHistoria(@PathVariable Long id,@RequestBody HistoriaMedica historiaMedica){
        Optional<HistoriaMedica> o = historiaMedicaService.buscarPorIdHistoria(id);
        if(o.isPresent()) {
            HistoriaMedica HistDB = o.get();
            HistDB.setPaciente(HistDB.getPaciente());
            HistDB.setAlergias(HistDB.getAlergias());
            HistDB.setEnfermedadesCronicas(HistDB.getEnfermedadesCronicas());
            HistDB.setCirugiasPrevias(HistDB.getCirugiasPrevias());
            HistDB.setHospitalizacionesPrevias(HistDB.getHospitalizacionesPrevias());
            HistDB.setMedicamentosActuales(HistDB.getMedicamentosActuales());
            HistDB.setResultadosExamenesPrevios(HistDB.getResultadosExamenesPrevios());
            return ResponseEntity.status(HttpStatus.CREATED).body(historiaMedicaService.guardarHistoria(HistDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> eliminarHistoria(@PathVariable Long id){
        Optional<HistoriaMedica> o = historiaMedicaService.buscarPorIdHistoria(id);
        if(o.isPresent()) {
            historiaMedicaService.eliminarHistoria(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
