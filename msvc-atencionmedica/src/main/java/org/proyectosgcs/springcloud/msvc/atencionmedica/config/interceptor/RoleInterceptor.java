package org.proyectosgcs.springcloud.msvc.atencionmedica.config.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proyectosgcs.springcloud.msvc.atencionmedica.Auth.JwtAuthorizationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtAuthorizationHelper jwtAuthorizationHelper;

    @Autowired
    private ObjectMapper objectMapper;

    // Define las rutas que deben ser excluidas de la autenticación
    private static final List<String> EXCLUDED_PATHS = List.of("/api/auth/login", "/api/auth/loginAdmin");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        // Verifica si la ruta está en la lista de exclusiones
        if (EXCLUDED_PATHS.contains(path)) {
            return true;
        }

        String[] rolesAllowed = getRolesAllowed(handler);
        if (rolesAllowed != null && rolesAllowed.length > 0) {
            for (String role : rolesAllowed) {
                if (jwtAuthorizationHelper.validarRol(request, role)) {
                    return true;
                }
            }
        }

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(Map.of(
                "status", "error",
                "message", "Acceso denegado"
        )));
        return false;
    }

    private String[] getRolesAllowed(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RolesAllowed annotation = handlerMethod.getMethodAnnotation(RolesAllowed.class);
            if (annotation != null) {
                return annotation.value();
            }
        }
        return null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Do nothing
    }
}
