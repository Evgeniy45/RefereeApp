package com.basketball.refereeapp.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI refereeApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Basketball Referee API")
                        .description("REST API для керування арбітрами, матчами та призначеннями")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Evgeniy")
                                .email("you@example.com"))
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub проекту")
                        .url("https://github.com/Evgeniy45/RefereeApp"))
                .servers(List.of(new Server().url("http://localhost:8080").description("Local Dev")));
    }
}