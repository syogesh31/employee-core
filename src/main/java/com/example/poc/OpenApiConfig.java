package com.example.poc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
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
                        .addSecuritySchemes("google_oidc", new SecurityScheme()
                                .type(SecurityScheme.Type.OPENIDCONNECT)
                                .openIdConnectUrl("https://accounts.google.com/.well-known/openid-configuration")
                                .description("Google OpenID Connect (Uses ID Token)"))
                        .addSecuritySchemes("manual_jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Fallback: Paste the 'id_token' (JWT) here if 'google_oidc' fails")))
                .addSecurityItem(new SecurityRequirement().addList("google_oidc"))
                .addSecurityItem(new SecurityRequirement().addList("manual_jwt"));
    }
}
