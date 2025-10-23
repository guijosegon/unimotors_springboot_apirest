package br.com.unimotors.anuncio.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record AnuncioRespostaDTO(
        UUID id,
        String titulo,
        String descricao,
        BigDecimal preco,
        String status,
        String cidade,
        String estado,
        String marca,
        String modelo,
        Integer ano,
        List<String> imagens,
        Set<String> opcionais,
        UUID proprietarioId
) {
}
