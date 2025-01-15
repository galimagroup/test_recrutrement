package com.alten.e_commerce.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.alten.e_commerce.auth.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private static final String[] WHITE_LIST_URL = {
    "/v2/api-docs",
    "/v2/api-docs/**",
    "/v3/api-docs",
    "/v3/api-docs/**",
    "/v3/**",
    "/swagger.json",
    "/swagger-resources",
    "/swagger-resources/**",
    "/configuration/ui",
    "/configuration/security",
    "/swagger-ui/**",
    "/webjars/**",
    "/swagger-ui.html",
    "/api/v1/auth/**",
    "/h2-console/",
    "/h2-console/**"
  };
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(req ->
        req
          .requestMatchers(WHITE_LIST_URL)
          .permitAll()
          .requestMatchers(POST, "/products")
          .hasAnyAuthority("admin@admin.com")
          .requestMatchers(PUT, "/products/**")
          .hasAnyAuthority("admin@admin.com")
          .requestMatchers(PATCH, "/products/**")
          .hasAnyAuthority("admin@admin.com")
          .requestMatchers(DELETE, "/products/**")
          .hasAnyAuthority("admin@admin.com")
          .anyRequest()
          .authenticated()
      )
      .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(
        jwtAuthFilter,
        UsernamePasswordAuthenticationFilter.class
      )
      .logout(logout ->
        logout
          .logoutUrl("/api/v1/auth/logout")
          .addLogoutHandler(logoutHandler)
          .logoutSuccessHandler((request, response, authentication) ->
            SecurityContextHolder.clearContext()
          )
      );

      http.headers(headers -> 
              headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

    return http.build();
  }
}
