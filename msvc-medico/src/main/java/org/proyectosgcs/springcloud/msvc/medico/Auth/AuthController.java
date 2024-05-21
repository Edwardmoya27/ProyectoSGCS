package org.proyectosgcs.springcloud.msvc.medico.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.proyectosgcs.springcloud.msvc.medico.models.entity.Medico;
import org.proyectosgcs.springcloud.msvc.medico.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static String JWT_SECRET = "8p5sBZD84u2cP7wjM6YSZwTz0G2tP1qosKLKvIMgpJU";
    @Autowired
    private MedicoService medicoService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> dniData) {
        String dni = dniData.get("dni");
        if (dni == null || dni.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "El DNI no puede estar vacío"
            ));
        }
        Optional<Medico> medicoOptional = medicoService.obtenerMedicoPorDni(dni);
        if (medicoOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "No se encuentra el médico. Por favor verifica tu DNI"
            ));
        Medico medico = medicoOptional.get();
        String token = generarTokenJWT(medico);
        return ResponseEntity.ok(Map.of("token", token, "status","ok","data", medico));
    }

    // Método para generar un token JWT
    private String generarTokenJWT(Medico medico) {
        Claims claims = Jwts.claims().setSubject(medico.getDni());
        claims.put("roles", List.of("MEDICO"));
        claims.put("medicoId", medico.getId());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }
}
