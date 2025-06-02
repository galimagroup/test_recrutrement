package com.architech.test.product.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
public class AdminAccessInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AdminAccessInterceptor.class);
    private static final String ADMIN_EMAIL = "admin@admin.com";
    private static final String USER_EMAIL_HEADER = "X-User-Email";

    private static final List<String> RESTRICTED_METHODS = Arrays.asList("POST", "PUT", "PATCH", "DELETE");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api/product") && RESTRICTED_METHODS.contains(method)) {
            String userEmail = request.getHeader(USER_EMAIL_HEADER);

            log.debug("Vérification d'accès admin pour {} {} avec email: {}", method, requestURI, userEmail);

            if (userEmail == null || userEmail.trim().isEmpty()) {
                log.warn("Tentative d'accès sans email pour {} {}", method, requestURI);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Email requis dans le header X-User-Email\"}");
                return false;
            }

            if (!ADMIN_EMAIL.equals(userEmail.trim())) {
                log.warn("Tentative d'accès non autorisé avec email: {} pour {} {}", userEmail, method, requestURI);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Accès refusé. Seul l'administrateur peut effectuer cette action.\"}");
                return false;
            }

            log.info("Accès admin autorisé pour {} {}", method, requestURI);
        }

        return true;
    }
}
