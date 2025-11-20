package br.com.unimotors.catalogo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EspecificacaoVeiculoCriarDTO(
        @NotNull UUID modeloId,
        @Min(1950) @Max(2100) Integer ano,
        String combustivel,
        String cambio,
        String carroceria,
        @Min(1) @Max(6) Integer portas
) {
}

