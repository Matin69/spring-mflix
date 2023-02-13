package com.mflix.gateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final ServicesAuthenticationProvider servicesAuthenticationProvider;

    private final UserDetailsService userDetailsService;

    public SecurityConfigurer(ServicesAuthenticationProvider servicesAuthenticationProvider, UserDetailsService userDetailsService) {
        this.servicesAuthenticationProvider = servicesAuthenticationProvider;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if (activeProfile.equals("dev")) {
            return configDevSecurity(http);
        } else {
            return configProductionSecurity(http);
        }
    }

    @Bean
    public ServicesAuthenticationFilter servicesAuthenticationFilter(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(servicesAuthenticationProvider);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        return new ServicesAuthenticationFilter(authenticationManager);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private SecurityFilterChain configProductionSecurity(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .anyRequest().hasAuthority("ROLE_USER")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterAfter(servicesAuthenticationFilter(http), ExceptionTranslationFilter.class)
                .formLogin().disable()
                .httpBasic().disable()
                .cors().disable()
                .csrf().disable()
                .build();
    }

    private SecurityFilterChain configDevSecurity(HttpSecurity http) throws Exception {
        return http.build();
    }
}
