package br.com.unimotors.anuncio.model;

import br.com.unimotors.catalogo.model.Modelo;
import br.com.unimotors.usuario.model.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Column(nullable = false, length = 140)
    private String titulo;

    @Column(columnDefinition = "text")
    private String descricao;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false, length = 12)
    private String status;

    @Column(nullable = false, length = 80)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    public AnuncioVeiculo() { this.id = UUID.randomUUID(); this.criadoEm = LocalDateTime.now(); }

    // Minimal constructor
    public AnuncioVeiculo(Usuario proprietario, Modelo modelo, String titulo, BigDecimal preco, String cidade, String estado) {
        this();
        this.proprietario = proprietario;
        this.modelo = modelo;
        this.titulo = titulo;
        this.preco = preco;
        this.status = "RASCUNHO";
        this.cidade = cidade;
        this.estado = estado;
    }

    // getters/setters omitted for brevity (can be generated in IDE)
    public UUID getId() { return id; }
    public Usuario getProprietario() { return proprietario; }
    public void setProprietario(Usuario proprietario) { this.proprietario = proprietario; }
    public Modelo getModelo() { return modelo; }
    public void setModelo(Modelo modelo) { this.modelo = modelo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
