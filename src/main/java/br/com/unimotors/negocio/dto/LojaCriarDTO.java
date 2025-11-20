package br.com.unimotors.negocio.dto;

import jakarta.validation.constraints.NotBlank;

public record LojaCriarDTO(
        @NotBlank String nome,
        String cnpj,
        @NotBlank String cidade,
        @NotBlank String estado
) {
}

