package org.proyectosgcs.springcloud.msvc.atencionmedica.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Auth.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    private final JwtTokenService jwtTokenService;

    public FeignClientConfig(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String token = jwtTokenService.getToken();
                if (token != null) {
                    template.header("Authorization", "Bearer " + token);
                }
            }
        };
    }
}
