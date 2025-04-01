package sow.issa.recrutement.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import sow.issa.recrutement.handlers.CustomAccessDeniedHandler;
import sow.issa.recrutement.handlers.Http401UnauthorizedEntryPoint;
import sow.issa.recrutement.repositories.UserRepository;
import sow.issa.recrutement.security.jwt.JWTFilter;
import sow.issa.recrutement.security.jwt.TokenProvider;

@Configuration
@EnableWebSecurity
@Profile(value = "!test")
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {
    private final Http401UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/v2/api-docs",
                                "/configuration/ui",
                                "/swagger-resources/**",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/configuration/security",
                                "/swagger-ui.html",
                                "/manage/**",
                                "/api/*/v3/api-docs",
                                "/api/v3/api-docs/**",
                                "/api/*/v3/api-docs/**",
                                "/webjars/springfox-swagger-ui/**",
                                "/api/*/v2/api-docs",
                                "/account",
                                "/token"
                        ).permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(authz -> authz.anyRequest().authenticated());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private JWTFilter jwtFilter() {
       return new JWTFilter(tokenProvider, userRepository);
    }
}
