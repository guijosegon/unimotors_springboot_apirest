package br.com.unimotors.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI unimotorsOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("UniMotors API")
                        .description("API para marketplace de veículos")
                        .version("0.0.1"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .tags(List.of(
                        new Tag().name("Auth").description("Autenticação e JWT"),
                        new Tag().name("Usuario").description("Gestão de usuários (ADMIN)"),
                        new Tag().name("Catalogo").description("Marca, Modelo, Especificação e Opcionais"),
                        new Tag().name("Anuncio").description("Anúncios de veículos"),
                        new Tag().name("Proposta").description("Propostas de compra"),
                        new Tag().name("Favorito").description("Favoritos de anúncios"),
                        new Tag().name("Loja").description("Lojas e vínculos de usuários")
                ))
                .externalDocs(new ExternalDocumentation().description("Documentação do projeto"));
    }
}
