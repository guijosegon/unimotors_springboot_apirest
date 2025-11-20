package br.com.unimotors.negocio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LojaMembroDTO(
        @NotNull UUID usuarioId,
        @NotBlank String papelNaLoja
) {
}

