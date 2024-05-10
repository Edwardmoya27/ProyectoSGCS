package org.proyectsgcs.springcloud.msvc.medicamento.controllers;


import jakarta.validation.Valid;
import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.Proveedor;
import org.proyectsgcs.springcloud.msvc.medicamento.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/proveedor")
public class ProveedorController {
    @Autowired
    private ProveedorService service;
    @GetMapping
    public List<Proveedor> listar(){
        return service.listar();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle (@PathVariable Long id){
        Optional<Proveedor> postulaOptional = service.porId(id);
        if(postulaOptional.isPresent()){

            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("Mensaje", "SI existe el proveedor "));
        }
        return  ResponseEntity.badRequest().body(
                Collections.singletonMap("Mensaje", "NO existe el proveedor "));
    }
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Proveedor proveedor, BindingResult result){

        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(proveedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid@RequestBody Proveedor proveedor, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return Validando(result);
        }
        Optional<Proveedor> pos = service.porId(id);
        if(pos.isPresent()){
            Proveedor postulAux = pos.get();
            postulAux.setNombre(proveedor.getNombre());
            postulAux.setDireccion(proveedor.getDireccion());
            postulAux.setTelefono(proveedor.getTelefono());
            postulAux.setCorreoElectronico(proveedor.getCorreoElectronico());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(postulAux));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Proveedor> pos = service.porId(id);
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
