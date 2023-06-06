package de.bht.planningpoker.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("http://localhost:${server.port}")
    private String localUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .components(apiComponents())
                .addServersItem(apiLocalServer());
    }

    private Info apiInfo() {
        return new Info()
                .title("Planning Poker API")
                .description("The documentation of the API")
                .version("1.0");
    }

    private Components apiComponents() {
        return new Components()
                .addSecuritySchemes("bearer-auth", apiBearerAuth());
    }

    private SecurityScheme apiBearerAuth() {
        return new SecurityScheme()
                .name("bearer-auth")
                .description("Bearer token")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);
    }

    private Server apiLocalServer() {
        return new Server()
                .description("LOCAL")
                .url(localUrl);
    }

}
