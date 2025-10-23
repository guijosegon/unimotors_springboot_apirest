package br.com.unimotors.usuario.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioRespostaDTO(UUID id, String nome, String email, String perfil, String telefone, LocalDateTime criadoEm) {
}
