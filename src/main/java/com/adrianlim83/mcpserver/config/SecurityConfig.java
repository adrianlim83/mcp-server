package com.adrianlim83.mcpserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${mcp.jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity in this prototype
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/.well-known/**").permitAll() // Domain verification endpoint
                .requestMatchers("/v1/mcp").authenticated() // Protected MCP endpoint
                .anyRequest().permitAll()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.decoder(jwtDecoder()))
            );

        return http.build();
    }

    /**
     * JWT Decoder for validating JWTs.
     * For this walking skeleton, we use a simple HMAC-based decoder.
     * In production, this should use JWK Set URI from a real OAuth2 provider.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        // For prototype: use configured secret key
        // In production: use spring.security.oauth2.resourceserver.jwt.jwk-set-uri
        
        try {
            // Create a 256-bit key for HS256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(jwtSecret.getBytes(StandardCharsets.UTF_8));
            SecretKey secretKey = new SecretKeySpec(hash, "HmacSHA256");
            
            return NimbusJwtDecoder.withSecretKey(secretKey).build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to create JWT decoder", e);
        }
    }
}
