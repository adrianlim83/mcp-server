package com.adrianlim83.mcpserver.model;

/**
 * MCP Response model
 * Represents an outgoing MCP response
 */
public class McpResponse {
    private String message;
    private long timestamp;

    public McpResponse() {
    }

    public McpResponse(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
