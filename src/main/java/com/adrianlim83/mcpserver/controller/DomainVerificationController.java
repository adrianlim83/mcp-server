package com.adrianlim83.mcpserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Domain Verification Controller
 * Provides endpoints for OAuth2 provider domain verification
 */
@RestController
@RequestMapping("/.well-known")
public class DomainVerificationController {

    @Value("${mcp.domain.verification.token}")
    private String verificationToken;

    /**
     * Domain verification endpoint
     * Returns the verification token required by OAuth2 providers
     */
    @GetMapping("/mcp-configuration")
    public ResponseEntity<Map<String, String>> getDomainVerification() {
        return ResponseEntity.ok(Map.of(
            "verification_token", verificationToken
        ));
    }
}
