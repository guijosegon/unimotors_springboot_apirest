package br.com.unimotors.negocio.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "lojas")
public class Loja {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, length = 160)
    private String nome;

    @Column(length = 18, unique = true)
    private String cnpj;

    @Column(nullable = false, length = 80)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    public Loja() {
        this.id = UUID.randomUUID();
    }

    public Loja(String nome, String cnpj, String cidade, String estado) {
        this();
        this.nome = nome;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.estado = estado;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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
}

