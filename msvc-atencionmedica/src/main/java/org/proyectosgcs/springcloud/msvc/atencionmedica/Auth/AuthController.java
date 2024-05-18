package org.proyectosgcs.springcloud.msvc.atencionmedica.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static String jwtSecret = "8p5sBZD84u2cP7wjM6YSZwTz0G2tP1qosKLKvIMgpJU";
    private static String admin_email = "admin@proyectosgcs.org";
    private static String admin_password = "proyectosgcs";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
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

    // Método para generar un token JWT con roles
    private String generarTokenJWT(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", List.of(role));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}
