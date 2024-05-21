package org.proyectosgcs.springcloud.msvc.atencionmedica.Auth;

import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    public void setToken(String token) {
        tokenHolder.set(token);
    }

    public String getToken() {
        return tokenHolder.get();
    }

    public void clearToken() {
        tokenHolder.remove();
    }
}
