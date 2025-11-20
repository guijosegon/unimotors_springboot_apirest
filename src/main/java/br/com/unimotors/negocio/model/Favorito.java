package br.com.unimotors.negocio.model;

import br.com.unimotors.anuncio.model.AnuncioVeiculo;
import br.com.unimotors.usuario.model.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "favoritos")
public class Favorito {

    @EmbeddedId
    private FavoritoId id;

    @ManyToOne(optional = false)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @MapsId("anuncioId")
    @JoinColumn(name = "anuncio_id")
    private AnuncioVeiculo anuncio;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    public Favorito() {
        this.criadoEm = LocalDateTime.now();
    }

    public Favorito(Usuario usuario, AnuncioVeiculo anuncio) {
        this();
        this.usuario = usuario;
        this.anuncio = anuncio;
        this.id = new FavoritoId(usuario.getId(), anuncio.getId());
    }

    public FavoritoId getId() {
        return id;
    }

    public void setId(FavoritoId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public AnuncioVeiculo getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(AnuncioVeiculo anuncio) {
        this.anuncio = anuncio;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}

