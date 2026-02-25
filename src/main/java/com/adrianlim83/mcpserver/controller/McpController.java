package com.adrianlim83.mcpserver.controller;

import com.adrianlim83.mcpserver.model.McpRequest;
import com.adrianlim83.mcpserver.model.McpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * MCP Controller
 * Handles MCP protocol requests with OAuth2 JWT authentication
 */
@RestController
@RequestMapping("/v1")
public class McpController {

    /**
     * Main MCP endpoint
     * Protected by OAuth2 JWT authentication
     * Implements simple ping-pong logic for the walking skeleton
     */
    @PostMapping("/mcp")
    public ResponseEntity<McpResponse> handleMcpRequest(
            @RequestBody McpRequest request,
            Authentication authentication) {
        
        // Simple ping-pong logic
        String responseMessage;
        if ("ping".equalsIgnoreCase(request.getMessage())) {
            responseMessage = "pong";
        } else {
            responseMessage = "Message received: " + request.getMessage();
        }

        McpResponse response = new McpResponse(responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint (also protected)
     * Can be used to verify authentication is working
     */
    @GetMapping("/mcp/health")
    public ResponseEntity<McpResponse> healthCheck(Authentication authentication) {
        String username = authentication != null ? authentication.getName() : "anonymous";
        McpResponse response = new McpResponse("MCP Server is healthy. Authenticated as: " + username);
        return ResponseEntity.ok(response);
    }
}
