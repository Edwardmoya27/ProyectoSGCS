package org.proyectosgcs.springcloud.msvc.atencionmedica.Controllers;

import feign.FeignException;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Cita;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Pago;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Services.CitasService;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @file: CitaController
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 05:20 p. m.
 */
@RestController
@RequestMapping("api/citas")
public class CitasController {
    @Autowired
    private CitasService citasService;

    @GetMapping
    public List<Cita> listarCitas(){
        return citasService.listarCitas();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorCita(@PathVariable Long id){
        Optional<Cita> CitasOptional = citasService.buscarPorIdCitas(id);
        if(CitasOptional.isPresent()) {
            return ResponseEntity.ok(CitasOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<?> crearCita(@RequestBody Cita cita){
        return ResponseEntity.status(HttpStatus.CREATED).body(citasService.guardarCitas(cita));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCita(@PathVariable Long id,@RequestBody Cita cita){
        Optional<Cita> o = citasService.buscarPorIdCitas(id);
        if(o.isPresent()) {
            Cita citaDB = o.get();
            citaDB.setFechaHora(cita.getFechaHora());
            citaDB.setEstado(cita.getEstado());
            citaDB.setPaciente(cita.getPaciente());
            citaDB.setMedicoId(cita.getMedicoId());
            citaDB.setMotivo(cita.getMotivo());
            return ResponseEntity.status(HttpStatus.CREATED).body(citasService.guardarCitas(citaDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> eliminarCitas(@PathVariable Long id){
        Optional<Cita> o = citasService.buscarPorIdCitas(id);
        if(o.isPresent()) {
            citasService.eliminarCitas(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
        }

    //metodos Remotos
    @PostMapping("/crear-pago/{citaId}")
    public ResponseEntity<?> crearPago(@RequestBody Pago pago, @PathVariable Long citaId){
        Optional<Pago> oPago;
        try {
            oPago = citasService.crearPago(pago, citaId);
        }
        catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje","No se pudo crear el pago"+
                            "o error en la comunicacion:"+e.getMessage()));
        }
        if (oPago.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).body(oPago.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<?> listarCitasPorIdMedico(@PathVariable Long idMedico){
        try {
           return ResponseEntity.ok().body(citasService.obtenerCitasPorIdMedico(idMedico));
        }
        catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje","No se pudo crear el pago"+
                            "o error en la comunicacion:"+e.getMessage()));
        }
    }

}
