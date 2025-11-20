package br.com.unimotors.anuncio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record AnuncioAtualizarDTO(
        @NotNull UUID modeloId,
        UUID especificacaoId,
        @NotBlank String titulo,
        @Size(max = 4000) String descricao,
        @Positive BigDecimal preco,
        @NotBlank String cidade,
        @NotBlank String estado,
        Set<UUID> opcionaisIds,
        List<@NotBlank String> urlsImagens
) {
}

