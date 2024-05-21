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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PacienteService pacienteService;
    private static String JWT_SECRET = "8p5sBZD84u2cP7wjM6YSZwTz0G2tP1qosKLKvIMgpJU";
    private static String admin_email = "admin@proyectosgcs.org";
    private static String admin_password = "proyectosgcs";

    @PostMapping("/loginAdmin")
    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Verifica si el email y la contraseña coinciden con el ADMIN
        if (admin_email.equals(email) && admin_password.equals(password)) {
            // Genera un token JWT con el rol ADMIN
                String token = generarTokenJWT(email, "ADMIN");
            return ResponseEntity.ok(Map.of("token", token, "status", "ok"));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Credenciales incorrectas. Por favor verifica tu email y contraseña"
            ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> dniData) {
        String dni = dniData.get("dni");
        if (dni == null || dni.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "El DNI no puede estar vacío"
            ));
        }
        Optional<Paciente> medicoOptional = pacienteService.obtenerPacientePorDni(dni);
        if (medicoOptional.isEmpty())
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "No se encuentra el paciente. Por favor verifica tu DNI"
            ));
        Paciente paciente = medicoOptional.get();
        String token = generarTokenJWTPaciente(paciente);
        return ResponseEntity.ok(Map.of("token", token, "status","ok","data", paciente));
    }


    // Método para generar un token JWT con roles
    private String generarTokenJWT(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", List.of(role));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    private String generarTokenJWTPaciente(Paciente paciente) {
        Claims claims = Jwts.claims().setSubject(paciente.getDni());
        claims.put("roles", List.of("PACIENTE"));
        claims.put("pacienteId", paciente.getId());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

}

