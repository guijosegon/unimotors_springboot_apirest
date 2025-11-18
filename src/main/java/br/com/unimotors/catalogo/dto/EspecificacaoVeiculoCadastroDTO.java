package br.com.unimotors.catalogo.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record EspecificacaoVeiculoCadastroDTO(

        @NotNull(message = "O modelo do veículo é obrigatório.")
        UUID modeloId,

        @Min(1950)
        @Max(2100)
        Integer ano,

        @Size(max = 20)
        String combustivel,

        @Size(max = 20)
        String cambio,

        @Size(max = 30)
        String carroceria,

        @Min(1) @Max(6)
        Integer portas
) {}
