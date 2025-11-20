package br.com.unimotors.negocio.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class FavoritoId implements Serializable {

    private UUID usuarioId;
    private UUID anuncioId;

    public FavoritoId() {
    }

    public FavoritoId(UUID usuarioId, UUID anuncioId) {
        this.usuarioId = usuarioId;
        this.anuncioId = anuncioId;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public UUID getAnuncioId() {
        return anuncioId;
    }

    public void setAnuncioId(UUID anuncioId) {
        this.anuncioId = anuncioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritoId that = (FavoritoId) o;
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(anuncioId, that.anuncioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, anuncioId);
    }
}

