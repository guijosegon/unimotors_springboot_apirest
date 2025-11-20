package br.com.unimotors.negocio.dto;

import java.util.UUID;

public record LojaRespostaDTO(
        UUID id,
        String nome,
        String cnpj,
        String cidade,
        String estado
) {
}

