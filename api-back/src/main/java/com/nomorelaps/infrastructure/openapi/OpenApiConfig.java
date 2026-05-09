package com.nomorelaps.infrastructure.openapi;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configuration class for OpenAPI (Swagger) documentation.
 * Configures API metadata and JWT security schemes for REST endpoints.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "Bearer Authentication";
    private static final String API_KEY_SCHEME_NAME = "API Key Authentication";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("NoMoreLaps API")
                        .version("1.0.0")
                        .description("Full-stack application for Smart Parking Management. ")
                        .contact(new Contact()
                                .name("nexphernandez")
                                .url("https://github.com/nexphernandez"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .addSecurityItem(new SecurityRequirement().addList(API_KEY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Enter your JWT token in the format: {token}"))
                        .addSecuritySchemes(API_KEY_SCHEME_NAME, new SecurityScheme()
                                .name("X-API-KEY")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .description("Enter your Company API Key to authenticate external requests.")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("FullAPI")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public GroupedOpenApi companyApi() {
        return GroupedOpenApi.builder()
                .group("CompanyAPI")
                .pathsToMatch(
                    "/api/parkings/**",
                    "/api/parking-spots/**",
                    "/api/reservations/**",
                    "/api/notifications/**",
                    "/api/sanctions/**",
                    "/api/dynamic-prices/**"
                )
                .pathsToExclude(
                    "/api/parkings/nearby",
                    "/api/parkings/search",
                    "/api/reservations/user/**",
                    "/api/users/**",
                    "/api/auth/**",
                    "/api/smart-calendar/**"
                )
                .build();
    }
}
