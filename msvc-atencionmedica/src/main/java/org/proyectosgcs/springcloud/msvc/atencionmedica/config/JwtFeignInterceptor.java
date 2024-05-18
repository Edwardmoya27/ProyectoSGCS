package org.proyectosgcs.springcloud.msvc.atencionmedica.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class JwtFeignInterceptor implements RequestInterceptor {

    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    public static void setToken(String token) {
        tokenHolder.set(token);
    }

    public static void clearToken() {
        tokenHolder.remove();
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = tokenHolder.get();
        if (token != null) {
            requestTemplate.header("Authorization", "Bearer " + token);
        }
    }
}
