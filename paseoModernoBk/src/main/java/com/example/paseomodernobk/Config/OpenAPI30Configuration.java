package com.example.paseomodernobk.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@formatter:off
@OpenAPIDefinition(
        info = @Info(title = "Paseo Moderno DOCS", version = "V1",
                contact = @Contact(name = "Adi Danut Mihalachi", email = "daw09.2022@gmail.com"),
                license = @License(name = "Swagger", url = ""), termsOfService = "None",
                description = "Una tienda online de ropa y accesorios"),
        servers = {
                @Server(url = "http://localhost:8080", description = "Development"),
                @Server(url = "${api.server.url}", description = "Production")})
//@formatter:on
public class OpenAPI30Configuration {

    /**
     * Configure the OpenAPI components.
     *
     * @return Returns fully configure OpenAPI object
     * @see OpenAPI
     */
    @Bean
    public OpenAPI customizeOpenAPI() {
        //@formatter:off
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .description(
                                        ("Inserte un token JWT que obtendra al hacer Login o Register en 'auth-controller'"))
                                .bearerFormat("JWT")));
        //@formatter:on

    }
}