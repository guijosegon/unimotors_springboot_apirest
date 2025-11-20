package br.com.unimotors.anuncio.model;

import br.com.unimotors.catalogo.model.EspecificacaoVeiculo;
import br.com.unimotors.catalogo.model.Modelo;
import br.com.unimotors.catalogo.model.Opcional;
import br.com.unimotors.usuario.model.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "anuncios")
public class AnuncioVeiculo {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "proprietario_id")
    private Usuario proprietario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "especificacao_id")
    private EspecificacaoVeiculo especificacao;

    @Column(nullable = false, length = 140)
    private String titulo;

    @Column(columnDefinition = "text")
    private String descricao;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private StatusAnuncio status;

    @Column(nullable = false, length = 80)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "anuncio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagemAnuncio> imagens = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "anuncios_opcionais",
            joinColumns = @JoinColumn(name = "anuncio_id"),
            inverseJoinColumns = @JoinColumn(name = "opcional_id")
    )
    private Set<Opcional> opcionais = new HashSet<>();

    public AnuncioVeiculo() {
        this.id = UUID.randomUUID();
        this.criadoEm = LocalDateTime.now();
    }

    // Minimal constructor
    public AnuncioVeiculo(Usuario proprietario, Modelo modelo, String titulo, BigDecimal preco, String cidade, String estado) {
        this();
        this.proprietario = proprietario;
        this.modelo = modelo;
        this.titulo = titulo;
        this.preco = preco;
        this.status = StatusAnuncio.RASCUNHO;
        this.cidade = cidade;
        this.estado = estado;
    }

    public UUID getId() {
        return id;
    }

    public Usuario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Usuario proprietario) {
        this.proprietario = proprietario;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public EspecificacaoVeiculo getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(EspecificacaoVeiculo especificacao) {
        this.especificacao = especificacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public StatusAnuncio getStatus() {
        return status;
    }

    public void setStatus(StatusAnuncio status) {
        this.status = status;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public List<ImagemAnuncio> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemAnuncio> imagens) {
        this.imagens = imagens;
    }

    public Set<Opcional> getOpcionais() {
        return opcionais;
    }

    public void setOpcionais(Set<Opcional> opcionais) {
        this.opcionais = opcionais;
    }
}
