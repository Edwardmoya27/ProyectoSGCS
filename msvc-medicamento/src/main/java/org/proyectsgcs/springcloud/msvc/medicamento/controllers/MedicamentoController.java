package org.proyectsgcs.springcloud.msvc.medicamento.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.proyectsgcs.springcloud.msvc.medicamento.Auth.JwtAuthorizationHelper;
import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.Categoria;
import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.Medicamento;
import org.proyectsgcs.springcloud.msvc.medicamento.services.CategoriaService;
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
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private JwtAuthorizationHelper jwtAuthorizationHelper;

    @GetMapping
    public ResponseEntity<?> listar(HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN") &&
                !jwtAuthorizationHelper.validarRol(request, "MEDICO") &&
                !jwtAuthorizationHelper.validarRol(request, "PACIENTE")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle (@PathVariable Long id, HttpServletRequest request){

        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN") &&
                !jwtAuthorizationHelper.validarRol(request, "MEDICO") &&
                !jwtAuthorizationHelper.validarRol(request, "PACIENTE")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

        Optional<Medicamento> medicamentoOptional = service.porId(id);
        if(medicamentoOptional.isEmpty()){
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("Mensaje", "No existe el medicamento con el ID " + id));
        }
        return ResponseEntity.ok().body(medicamentoOptional.get());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Medicamento medicamento, BindingResult result, HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        Optional<Categoria> categoriaOptional = categoriaService.porId(medicamento.getCategoria().getId());
        if(categoriaOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "La categoría no existe"));

        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(medicamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid@RequestBody Medicamento medicamento, BindingResult result, @PathVariable Long id, HttpServletRequest request){

        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

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
    public ResponseEntity<?> eliminar(@PathVariable Long id, HttpServletRequest request){

        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

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
