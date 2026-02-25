package com.adrianlim83.mcpserver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DomainVerificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testDomainVerificationEndpoint_ReturnsToken() throws Exception {
        mockMvc.perform(get("/.well-known/mcp-configuration"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verification_token").value("mcp-server-verification-token-12345"));
    }
}
