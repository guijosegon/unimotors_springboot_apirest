package br.com.unimotors.negocio.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PropostaRespostaDTO(
        UUID id,
        UUID anuncioId,
        UUID compradorId,
        BigDecimal valor,
        String status,
        LocalDateTime criadoEm
) {
}

