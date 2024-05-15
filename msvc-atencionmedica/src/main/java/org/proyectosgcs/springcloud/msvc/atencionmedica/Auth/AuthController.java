package org.proyectosgcs.springcloud.msvc.atencionmedica.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Models.Entity.Paciente;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * @file: AuthController
 * @author: EdwarMoya
 * @created: 14/05/2024
 * @HoraCreated: 05:56 a. m.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static String jwtSecret = "8p5sBZD84u2cP7wjM6YSZwTz0G2tP1qosKLKvIMgpJU";
    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> dniData) {
        String dni = dniData.get("dni");
        if (dni == null || dni.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "El DNI no puede estar vacío"
            ));
        }
        Optional<Paciente> pacienteOptional = pacienteService.obtenerPacientePorDni(dni);
        if (pacienteOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "El paciente con el DNI " + dni + " no existe"
            ));
        String token = generarTokenJWT(dni);
        return ResponseEntity.ok(Map.of("token", token, "status","ok","data", pacienteOptional.get()));
    }
    // Método para generar un token JWT
    private String generarTokenJWT(String dni) {
        Claims claims = Jwts.claims().setSubject(dni);
        claims.put("roles", Arrays.asList("PACIENTE"));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}
