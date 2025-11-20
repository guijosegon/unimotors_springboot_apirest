package br.com.unimotors.anuncio.dto;

import br.com.unimotors.anuncio.model.StatusAnuncio;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusAnuncioDTO(@NotNull StatusAnuncio status) {
}

