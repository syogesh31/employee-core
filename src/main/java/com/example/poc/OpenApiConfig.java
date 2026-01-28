package com.example.poc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("google_oauth", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .description("Google OAuth2 (Note: Google sends Opaque Access Token, which might cause 'Malformed token' errors in this POC)")
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("https://accounts.google.com/o/oauth2/v2/auth")
                                                .tokenUrl("https://oauth2.googleapis.com/token")
                                                .scopes(new Scopes()
                                                        .addString("openid", "OpenID login")
                                                        .addString("profile", "Profile info")
                                                        .addString("email", "Email address")))))
                        .addSecuritySchemes("manual_jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Paste the 'id_token' (JWT) here if 'google_oauth' fails with 'Malformed token'")))
                .addSecurityItem(new SecurityRequirement()
                        .addList("google_oauth")
                        .addList("manual_jwt"));
    }
}
