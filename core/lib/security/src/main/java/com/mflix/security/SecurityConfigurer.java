package com.mflix.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
@EnableWebSecurity
@Profile("security")
public class SecurityConfigurer {

    private static final String READ_MOVIES_PERMISSION = "READ_MOVIES";

    private static final String WRITE_MOVIES_PERMISSION = "WRITE_MOVIES";

    private static final String READ_COMMENTS_PERMISSION = "READ_COMMENTS";

    private static final String WRITE_COMMENTS_PERMISSION = "WRITE_COMMENTS";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfigurer(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/movies/**").hasAuthority(READ_MOVIES_PERMISSION)
                        .requestMatchers("/movies/**").hasAuthority(WRITE_MOVIES_PERMISSION)
                        .requestMatchers(HttpMethod.GET, "/comments/**").hasAuthority(READ_COMMENTS_PERMISSION)
                        .requestMatchers("/comments/**").hasAuthority(WRITE_COMMENTS_PERMISSION)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, ExceptionTranslationFilter.class)
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .build();
    }
}
