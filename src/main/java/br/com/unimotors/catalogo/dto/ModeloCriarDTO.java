package br.com.unimotors.catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ModeloCriarDTO(
        @NotNull UUID marcaId,
        @NotBlank String nome
) {
}

