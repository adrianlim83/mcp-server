package com.adrianlim83.mcpserver.model;

/**
 * MCP Request model
 * Represents an incoming MCP request
 */
public class McpRequest {
    private String message;

    public McpRequest() {
    }

    public McpRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
