package org.proyectsgcs.springcloud.msvc.medicamento.controllers;


import jakarta.validation.Valid;
import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.HistorialSalidaMedicamentos;
import org.proyectsgcs.springcloud.msvc.medicamento.services.HistorialSalidaMedicamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/historialpedidos")
public class HistorialSalidaMedicamentosController {
    @Autowired
    private HistorialSalidaMedicamentosService service;
    @GetMapping
    public List<HistorialSalidaMedicamentos> listar(){
        return service.listar();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle (@PathVariable Long id){
        Optional<HistorialSalidaMedicamentos> postulaOptional = service.porId(id);
        if(postulaOptional.isPresent()){

            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("Mensaje", "SI existe historial"));
        }
        return  ResponseEntity.badRequest().body(
                Collections.singletonMap("Mensaje", "NO existe historial "));
    }
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody HistorialSalidaMedicamentos historialPedidos, BindingResult result){

        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(historialPedidos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid@RequestBody HistorialSalidaMedicamentos historialPedidos, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return Validando(result);
        }
        Optional<HistorialSalidaMedicamentos> pos = service.porId(id);
        if(pos.isPresent()){
            HistorialSalidaMedicamentos postulAux = pos.get();
            postulAux.setMedicamento(historialPedidos.getMedicamento());
            postulAux.setCantidadSolicitada(historialPedidos.getCantidadSolicitada());
            postulAux.setFechaPedido(historialPedidos.getFechaPedido());
            postulAux.setEstadoPedido(historialPedidos.getEstadoPedido());
            postulAux.setProveedor(historialPedidos.getProveedor());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(postulAux));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<HistorialSalidaMedicamentos> pos = service.porId(id);
        if(pos.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> Validando(BindingResult result) {
        Map<String, String> errores=new HashMap();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
