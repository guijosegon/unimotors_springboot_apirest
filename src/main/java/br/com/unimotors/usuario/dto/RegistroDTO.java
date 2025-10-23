package br.com.unimotors.usuario.dto;

import br.com.unimotors.usuario.model.PerfilAcesso;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistroDTO(
    @NotBlank String nome,
    @Email String email,
    @Size(min = 8, max = 64) String senha,
    @NotNull PerfilAcesso perfil
) {}
