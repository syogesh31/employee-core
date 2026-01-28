package com.example.poc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WhoAmIController.class)
@Import(SecurityConfig.class)
public class WhoAmIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenNoToken_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/whoami"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenToken_thenReturnClaims() throws Exception {
        mockMvc.perform(get("/whoami")
                .with(jwt().jwt(jwt -> jwt.claim("sub", "user123").claim("email", "user@example.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sub").value("user123"))
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }
}
