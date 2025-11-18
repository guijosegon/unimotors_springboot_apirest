package br.com.unimotors.anuncio.dto;

import java.util.UUID;

public record AnuncioOpcionalRespostaDTO(
        UUID id,
        UUID anuncioId,
        UUID opcionalId,
        String nomeOpcional
) {}
