package com.FabulasDeSapo.AdventureForge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // ‑ Los dos orígenes que realmente llamarán al backend
                .allowedOrigins(
                        "https://pisovacacional-e1ec8.web.app",
                        "http://localhost:4200")
                // ‑ Incluye OPTIONS y HEAD (pre‑flight)
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","HEAD")
                .allowedHeaders("*")
                // ‑ Solo si vas a enviar cookies / Authorization
                //.allowCredentials(true)
                .maxAge(3600);         // cachea la respuesta pre‑flight 1 h
    }
}