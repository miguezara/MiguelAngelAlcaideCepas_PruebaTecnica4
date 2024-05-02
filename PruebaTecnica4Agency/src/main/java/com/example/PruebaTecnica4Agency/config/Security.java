package com.example.PruebaTecnica4Agency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.ws.rs.HttpMethod;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class Security {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/agency/flights/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/agency/hotels/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/agency/clients/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/agency/clients/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/agency/hotel-booking/new").permitAll()
                                .requestMatchers(HttpMethod.POST, "/agency/flight-booking/new").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(withDefaults()); // Configurar inicio de sesión por formulario por defecto

        return http.build();
    }
}
