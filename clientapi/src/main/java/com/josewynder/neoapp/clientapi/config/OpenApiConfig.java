package com.josewynder.neoapp.clientapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI clientApiOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Client Management API")
                        .description("REST API to manage clients with CRUD operations and advanced search features.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("José Wynder")
                                .email("jose.wynder.hernandes@gmail.com")
                                .url("https://www.linkedin.com/in/jose-wynder/")
                        )
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Profile")
                        .url("https://github.com/JoseWynder")
                );
    }
}
