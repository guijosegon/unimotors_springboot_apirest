package br.com.unimotors.catalogo.dto;

import jakarta.validation.constraints.NotBlank;

public record OpcionalCriarDTO(@NotBlank String nome) {
}

