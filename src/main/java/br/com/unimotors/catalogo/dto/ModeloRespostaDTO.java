package br.com.unimotors.catalogo.dto;

import java.util.UUID;

public record ModeloRespostaDTO(
        UUID id,
        String nome,
        Integer ano,
        String categoria,
        UUID marcaId,
        String marcaNome
) {}
