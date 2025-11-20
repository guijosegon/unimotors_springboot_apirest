package br.com.unimotors.usuario.dto;

import br.com.unimotors.usuario.model.PerfilAcesso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioAtualizarDTO(
        @NotBlank String nome,
        @NotNull PerfilAcesso perfil,
        String telefone
) {
}

