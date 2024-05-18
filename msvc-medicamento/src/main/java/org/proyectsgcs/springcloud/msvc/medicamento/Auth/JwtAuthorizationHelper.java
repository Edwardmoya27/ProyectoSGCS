package org.proyectsgcs.springcloud.msvc.medicamento.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthorizationHelper {

    private static final String jwtSecret = "8p5sBZD84u2cP7wjM6YSZwTz0G2tP1qosKLKvIMgpJU";

    private List<String> getRolesFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
            List<String> roles = claims.get("roles", List.class);
            return roles;
        }
        return Collections.emptyList();
    }

    public boolean validarRol(HttpServletRequest request, String rol) {
        List<String> roles = getRolesFromToken(request);
        boolean resultado = roles.contains(rol);
        return resultado;
    }
}
