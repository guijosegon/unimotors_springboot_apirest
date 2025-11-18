package br.com.unimotors.anuncio.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AnuncioOpcionalCadastroDTO(

        @NotNull(message = "O ID do anúncio é obrigatório.")
        UUID anuncioId,

        @NotNull(message = "O ID do opcional é obrigatório.")
        UUID opcionalId

) {}
