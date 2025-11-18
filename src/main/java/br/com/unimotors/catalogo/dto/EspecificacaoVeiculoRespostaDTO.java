package br.com.unimotors.catalogo.dto;

import java.util.UUID;

public record EspecificacaoVeiculoRespostaDTO(
        UUID id,
        UUID modeloId,
        String modeloNome,
        Integer ano,
        String combustivel,
        String cambio,
        String carroceria,
        Integer portas
) {}
