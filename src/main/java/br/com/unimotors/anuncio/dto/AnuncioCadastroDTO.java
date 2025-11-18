package br.com.unimotors.anuncio.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record AnuncioCadastroDTO(

        @NotBlank(message = "Título é obrigatório.")
        @Size(max = 140)
        String titulo,

        @Size(max = 500)
        String descricao,

        @Positive(message = "Preço deve ser positivo.")
        BigDecimal preco,

        @NotBlank(message = "Cidade é obrigatória.")
        String cidade,

        @NotBlank(message = "Estado é obrigatório.")
        @Size(min = 2, max = 2)
        String estado,

        @NotNull(message = "Modelo do veículo é obrigatório.")
        UUID modeloId,

        @NotNull(message = "Ano é obrigatório.")
        @Min(value = 1900, message = "Ano inválido.")
        @Max(value = 2100, message = "Ano inválido.")
        Integer ano,

        List<String> imagens,

        Set<String> opcionais
) {}
