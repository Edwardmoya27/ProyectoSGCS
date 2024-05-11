package org.proyectsgcs.springcloud.msvc.medicamento.controllers;


import jakarta.validation.Valid;
import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.Medicamento;
import org.proyectsgcs.springcloud.msvc.medicamento.services.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {
    @Autowired
    private MedicamentoService service;

    @GetMapping
    public List<Medicamento> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle (@PathVariable Long id){
        Optional<Medicamento> medicamentoOptional = service.porId(id);
        if(medicamentoOptional.isEmpty()){
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("Mensaje", "No existe el medicamento con el ID " + id));
        }
        return ResponseEntity.ok().body(medicamentoOptional.get());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Medicamento medicamento, BindingResult result){

        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(medicamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid@RequestBody Medicamento medicamento, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return Validando(result);
        }
        Optional<Medicamento> medicamentoOptional = service.porId(id);
        if(medicamentoOptional.isEmpty()){
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("Mensaje", "No existe el medicamento con el ID " + id));
        }
        Medicamento medicamentoDB = medicamentoOptional.get();
        medicamentoDB.setNombre(medicamento.getNombre());
        medicamentoDB.setDescripcion(medicamento.getDescripcion());
        medicamentoDB.setPresentacion(medicamento.getPresentacion());
        medicamentoDB.setFabricante(medicamento.getFabricante());
        medicamentoDB.setFechaVencimiento(medicamento.getFechaVencimiento());
        medicamentoDB.setPrecio(medicamento.getPrecio());
        medicamentoDB.setStockDisponible(medicamento.getStockDisponible());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(medicamentoDB));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Medicamento> pos = service.porId(id);
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
