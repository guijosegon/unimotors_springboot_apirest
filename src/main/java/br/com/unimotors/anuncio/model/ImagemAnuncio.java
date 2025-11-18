package br.com.unimotors.anuncio.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "imagens_anuncio")
public class ImagemAnuncio {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "anuncio_id")
    private AnuncioVeiculo anuncio;

    @Column(nullable = false, length = 255)
    private String url;

    @Column(nullable = false)
    private Integer ordem;

    public ImagemAnuncio() {
        this.id = UUID.randomUUID();
    }

    public ImagemAnuncio(AnuncioVeiculo anuncio, String url, Integer ordem) {
        this();
        this.anuncio = anuncio;
        this.url = url;
        this.ordem = ordem;
    }

    public UUID getId() {
        return id;
    }

    public AnuncioVeiculo getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(AnuncioVeiculo anuncio) {
        this.anuncio = anuncio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }
}
