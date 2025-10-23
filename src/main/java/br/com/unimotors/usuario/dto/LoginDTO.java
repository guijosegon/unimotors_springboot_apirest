package br.com.unimotors.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
    @Email String email,
    @NotBlank String senha
) {}
