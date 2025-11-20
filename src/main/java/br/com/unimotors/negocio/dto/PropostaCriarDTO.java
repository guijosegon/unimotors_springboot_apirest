package br.com.unimotors.negocio.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PropostaCriarDTO(
        @Positive BigDecimal valor,
        @Size(max = 500) String mensagem
) {
}

