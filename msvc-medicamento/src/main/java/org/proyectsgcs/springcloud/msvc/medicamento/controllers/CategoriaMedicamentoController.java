package org.proyectsgcs.springcloud.msvc.medicamento.controllers;

import jakarta.validation.Valid;
import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.CategoriaMedicamento;
import org.proyectsgcs.springcloud.msvc.medicamento.services.CategoriaMedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaMedicamentoController {
    @Autowired
    private CategoriaMedicamentoService service;
    @GetMapping
    public List<CategoriaMedicamento> listar(){
        return service.listar();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle (@PathVariable Long id){
        Optional<CategoriaMedicamento> postulaOptional = service.porId(id);
        if(postulaOptional.isPresent()){

            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("Mensaje", "SI existe Categoria"));
        }
        return  ResponseEntity.badRequest().body(
                Collections.singletonMap("Mensaje", "NO existe Categoria "));
    }
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CategoriaMedicamento categoriaMedicamento, BindingResult result){

        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(categoriaMedicamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid@RequestBody CategoriaMedicamento categoriaMedicamento, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return Validando(result);
        }
        Optional<CategoriaMedicamento> pos = service.porId(id);
        if(pos.isPresent()){
            CategoriaMedicamento postulAux = pos.get();
            postulAux.setNombreCategoria(categoriaMedicamento.getNombreCategoria());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(postulAux));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<CategoriaMedicamento> pos = service.porId(id);
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
