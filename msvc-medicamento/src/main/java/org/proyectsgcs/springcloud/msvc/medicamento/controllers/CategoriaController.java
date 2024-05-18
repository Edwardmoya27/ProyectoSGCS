package org.proyectsgcs.springcloud.msvc.medicamento.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.proyectsgcs.springcloud.msvc.medicamento.Auth.JwtAuthorizationHelper;
import org.proyectsgcs.springcloud.msvc.medicamento.models.entity.Categoria;
import org.proyectsgcs.springcloud.msvc.medicamento.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService service;
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
        Optional<Categoria> categoriaMedicamentoOptional = service.porId(id);
        if(categoriaMedicamentoOptional.isEmpty()){
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("Mensaje", "No existe Categoria con el ID"));
        }
        return ResponseEntity.ok().body(categoriaMedicamentoOptional.get());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Categoria categoriaMedicamento,
                                   BindingResult result,
                                   HttpServletRequest request){
        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(categoriaMedicamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Categoria categoriaMedicamento,
                                    BindingResult result, @PathVariable Long id,
                                    HttpServletRequest request){

        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

        if(result.hasErrors()){
            return Validando(result);
        }
        Optional<Categoria> pos = service.porId(id);
        if(pos.isPresent()){
            Categoria postulAux = pos.get();
            postulAux.setNombre(categoriaMedicamento.getNombre());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(postulAux));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, HttpServletRequest request){

        if (!jwtAuthorizationHelper.validarRol(request, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "status", "error",
                    "message", "Acceso denegado"
            ));
        }

        Optional<Categoria> pos = service.porId(id);
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
