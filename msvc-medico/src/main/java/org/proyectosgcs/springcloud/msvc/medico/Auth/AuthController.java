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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static String jwtSecret = "8p5sBZD84u2cP7wjM6YSZwTz0G2tP1qosKLKvIMgpJU";
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
        String token = generarTokenJWT(dni);
        return ResponseEntity.ok(Map.of("token", token, "status","ok","data", medicoOptional.get()));
    }

    // Método para generar un token JWT
    private String generarTokenJWT(String dni) {
        Claims claims = Jwts.claims().setSubject(dni);
        claims.put("roles", List.of("PACIENTE"));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}
