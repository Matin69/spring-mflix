package com.mflix.gateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfigurer(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager, SuccessLoginHandler successLoginHandler) throws Exception {
        if (activeProfile.equals("dev")) {
            return configDevSecurity(http);
        } else {
            return configProductionSecurity(http, authenticationManager, successLoginHandler);
        }
    }

    private SecurityFilterChain configProductionSecurity(HttpSecurity http, AuthenticationManager authenticationManager, SuccessLoginHandler successLoginHandler) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .anyRequest().hasAuthority("ROLE_USER")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, ExceptionTranslationFilter.class)
                .addFilterBefore(usernamePasswordAuthenticationFilter(authenticationManager, successLoginHandler), JwtAuthenticationFilter.class)
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .build();
    }

    private SecurityFilterChain configDevSecurity(HttpSecurity http) throws Exception {
        return http.build();
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, SuccessLoginHandler successLoginHandler) throws Exception {
        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter(authenticationManager);
        usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(successLoginHandler);
        return usernamePasswordAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        return builder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
