package br.com.unimotors.catalogo.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "modelos", uniqueConstraints = {@UniqueConstraint(columnNames = {"marca_id","nome"})})
public class Modelo {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @Column(nullable = false, length = 120)
    private String nome;

    public Modelo() { this.id = UUID.randomUUID(); }
    public Modelo(Marca marca, String nome) { this(); this.marca = marca; this.nome = nome; }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) { this.marca = marca; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
