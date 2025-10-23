package br.com.unimotors.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI unimotorsOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("UniMotors API").description("API para marketplace de veículos").version("0.0.1"))
                .externalDocs(new ExternalDocumentation().description("Documentação do projeto"));
    }
}
