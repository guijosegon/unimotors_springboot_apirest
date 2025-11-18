package br.com.unimotors.anuncio.model;

import br.com.unimotors.catalogo.model.Opcional;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "anuncios_opcionais")
public class AnuncioOpcional {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "anuncio_id")
    private AnuncioVeiculo anuncio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "opcional_id")
    private Opcional opcional;

    public AnuncioOpcional() {
        this.id = UUID.randomUUID();
    }

    public AnuncioOpcional(AnuncioVeiculo anuncio, Opcional opcional) {
        this();
        this.anuncio = anuncio;
        this.opcional = opcional;
    }

    public UUID getId() {
        return id;
    }

    public AnuncioVeiculo getAnuncio() {
        return anuncio;
    }

    public Opcional getOpcional() {
        return opcional;
    }
}
