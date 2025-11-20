package br.com.unimotors.negocio.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class LojaUsuarioId implements Serializable {

    private UUID lojaId;
    private UUID usuarioId;

    public LojaUsuarioId() {
    }

    public LojaUsuarioId(UUID lojaId, UUID usuarioId) {
        this.lojaId = lojaId;
        this.usuarioId = usuarioId;
    }

    public UUID getLojaId() {
        return lojaId;
    }

    public void setLojaId(UUID lojaId) {
        this.lojaId = lojaId;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LojaUsuarioId that = (LojaUsuarioId) o;
        return Objects.equals(lojaId, that.lojaId) && Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lojaId, usuarioId);
    }
}

