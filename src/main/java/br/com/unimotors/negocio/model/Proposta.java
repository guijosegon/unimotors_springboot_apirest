package br.com.unimotors.negocio.model;

import br.com.unimotors.anuncio.model.AnuncioVeiculo;
import br.com.unimotors.usuario.model.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "propostas")
public class Proposta {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "anuncio_id")
    private AnuncioVeiculo anuncio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comprador_id")
    private Usuario comprador;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(length = 500)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private StatusProposta status;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    public Proposta() {
        this.id = UUID.randomUUID();
        this.criadoEm = LocalDateTime.now();
        this.status = StatusProposta.PENDENTE;
    }

    public Proposta(AnuncioVeiculo anuncio, Usuario comprador, BigDecimal valor, String mensagem) {
        this();
        this.anuncio = anuncio;
        this.comprador = comprador;
        this.valor = valor;
        this.mensagem = mensagem;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AnuncioVeiculo getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(AnuncioVeiculo anuncio) {
        this.anuncio = anuncio;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public void setComprador(Usuario comprador) {
        this.comprador = comprador;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public void setStatus(StatusProposta status) {
        this.status = status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}

