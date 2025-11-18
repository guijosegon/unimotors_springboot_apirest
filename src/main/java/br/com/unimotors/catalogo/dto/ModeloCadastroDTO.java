package br.com.unimotors.catalogo.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record ModeloCadastroDTO(

        @NotBlank(message = "Nome do modelo é obrigatório.")
        @Size(max = 120)
        String nome,

        @NotNull(message = "Ano é obrigatório.")
        @Min(1900)
        @Max(2100)
        Integer ano,

        @Size(max = 80)
        String categoria,

        @NotNull(message = "A marca do modelo é obrigatória.")
        UUID marcaId

) {}
