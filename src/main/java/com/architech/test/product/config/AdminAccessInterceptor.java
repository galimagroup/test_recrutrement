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

    private static final List<String> RESTRICTED_METHODS = Arrays.asList("POST", "PUT", "PATCH", "DELETE", "GET");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api/products") && RESTRICTED_METHODS.contains(method)) {
            String userEmail = request.getHeader(USER_EMAIL_HEADER);

            log.debug("Checking admin access for {} {} with email: {}", method, requestURI, userEmail);

            if (userEmail == null || userEmail.trim().isEmpty()) {
                log.warn("Access attempt without email for {} {}", method, requestURI);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Email required in X-User-Email header\"}");
                return false;
            }

            if (!ADMIN_EMAIL.equals(userEmail.trim())) {
                log.warn("Unauthorized access attempt with email: {} for {} {}", userEmail, method, requestURI);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Access denied. Only administrator can perform this action.\"}");
                return false;
            }

            log.info("Admin access granted for {} {}", method, requestURI);
        }

        return true;
    }
}
