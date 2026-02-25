package com.adrianlim83.mcpserver.util;

import io.jsonwebtoken.Jwts;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * JWT Token Generator Utility for Testing
 * Generates valid JWT tokens that can be used to test protected endpoints
 * 
 * Note: This uses the same secret as configured in application.properties (mcp.jwt.secret)
 */
public class JwtTokenGenerator {

    // This should match the value in application.properties: mcp.jwt.secret
    private static final String SECRET_STRING = "mcp-server-secret-key-for-jwt-validation-minimum-256-bits";

    public static String generateToken(String subject) {
        try {
            // Create the same secret key used by the application
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");

            // Create token with 1 hour expiration
            Date now = new Date();
            Date expiration = new Date(now.getTime() + 3600000); // 1 hour

            return Jwts.builder()
                    .subject(subject)
                    .issuedAt(now)
                    .expiration(expiration)
                    .signWith(secretKey)
                    .compact();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate JWT token", e);
        }
    }

    public static void main(String[] args) {
        // Generate a sample token for manual testing
        String token = generateToken("test-user");
        System.out.println("Generated JWT Token:");
        System.out.println(token);
        System.out.println("\nUse this token to test protected endpoints:");
        System.out.println("curl -X POST http://localhost:8080/v1/mcp \\");
        System.out.println("  -H \"Authorization: Bearer " + token + "\" \\");
        System.out.println("  -H \"Content-Type: application/json\" \\");
        System.out.println("  -d '{\"message\": \"ping\"}'");
    }
}
