package org.psk.lab.user.auth;

import org.psk.lab.user.service.AuthDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    private AuthDetailsService authDetailsService;

    public SecurityConfiguration(AuthDetailsService authDetailsService) {
        this.authDetailsService = authDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/register").permitAll();
                    registry.requestMatchers("/login").permitAll();
                    registry.requestMatchers("/api/user/**").hasRole("ADMIN");
                    registry.requestMatchers(
                            "/register", "/login",
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/webjars/**"
                    ).permitAll();
                    registry.requestMatchers(HttpMethod.POST, "/api/orders").hasRole("CUSTOMER");
                    registry.requestMatchers(HttpMethod.GET, "/api/orders/{orderId}").hasAnyRole("CUSTOMER", "ADMIN");
                    registry.requestMatchers(HttpMethod.PUT, "/api/orders/**/status").hasAnyRole("EMPLOYEE", "ADMIN");
                    registry.requestMatchers(HttpMethod.GET, "/api/orders").hasAnyRole("ADMIN", "EMPLOYEE");
                    registry.requestMatchers(HttpMethod.DELETE, "/api/orders/**").hasRole("ADMIN");
                    registry.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return authDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
