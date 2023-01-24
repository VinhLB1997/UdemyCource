package com.spring.blogapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String SCHEME = "Bearer";

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info().title("Blog API")
                        .description("Blog Udemy Cource")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"))
                .components(createComponents())
                .addSecurityItem(new SecurityRequirement().addList(AUTHORIZATION_HEADER));
    }

    private Components createComponents() {
        var components = new Components();
        components.addSecuritySchemes(AUTHORIZATION_HEADER, createSecurityScheme());

        return components;
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name(AUTHORIZATION_HEADER)
                .type(SecurityScheme.Type.HTTP)
                .scheme(SCHEME);
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("blog-api-public")
                .pathsToMatch("/**")
                .build();
    }
}
