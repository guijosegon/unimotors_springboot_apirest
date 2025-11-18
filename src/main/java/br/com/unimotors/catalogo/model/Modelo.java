package br.com.unimotors.catalogo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "modelos")
public class Modelo {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false)
    private Integer ano;

    @Column(length = 80)
    private String categoria; // SUV, Sedan, Hatch, etc.

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    public Modelo() {
        this.id = UUID.randomUUID();
        this.criadoEm = LocalDateTime.now();
    }

    public Modelo(Marca marca, String nome, Integer ano, String categoria) {
        this();
        this.marca = marca;
        this.nome = nome;
        this.ano = ano;
        this.categoria = categoria;
    }

    public UUID getId() { return id; }

    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) { this.marca = marca; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
}
