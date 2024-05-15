package org.proyectosgcs.springcloud.msvc.atencionmedica.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @file: JWTAutorizationHelper
 * @author: EdwarMoya
 * @created: 14/05/2024
 * @HoraCreated: 05:57 a. m.
 */
@Component
public class JWTAutorizationHelper {
    private static String jwtSecret = "8p5sBZD84u2cP7wjM6YSZwTz0G2tP1qosKLKvIMgpJU";

    public boolean validarRolMedico(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
            List<String> roles = claims.get("roles", List.class);
            if (roles != null && roles.contains("PACIENTE")) {
                return true;
            }
        }
        return false;
    }
    public boolean validarRolPaciente(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
            List<String> roles = claims.get("roles", List.class);
            if (roles != null && roles.contains("PACIENTE")) {
                return true;
            }
        }
        return false;
    }
    public boolean validarRolAdmin(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
            List<String> roles = claims.get("roles", List.class);
            if (roles != null && roles.contains("ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
