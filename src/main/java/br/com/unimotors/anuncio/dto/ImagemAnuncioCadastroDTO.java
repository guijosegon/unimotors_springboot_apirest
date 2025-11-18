package br.com.unimotors.anuncio.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record ImagemAnuncioCadastroDTO(

        @NotNull(message = "O ID do anúncio é obrigatório.")
        UUID anuncioId,

        @NotBlank(message = "A URL da imagem é obrigatória.")
        String url,

        @NotNull(message = "A ordem da imagem é obrigatória.")
        @Min(1)
        Integer ordem

) {}
