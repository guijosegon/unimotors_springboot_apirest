package br.com.unimotors.catalogo.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "opcionais")
public class Opcional {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true, length = 80)
    private String nome;

    public Opcional() {
        this.id = UUID.randomUUID();
    }

    public Opcional(String nome) {
        this();
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

