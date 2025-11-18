package br.com.unimotors.anuncio.dto;

import java.util.UUID;

public record ImagemAnuncioRespostaDTO(
        UUID id,
        String url,
        Integer ordem,
        UUID anuncioId
) {}
