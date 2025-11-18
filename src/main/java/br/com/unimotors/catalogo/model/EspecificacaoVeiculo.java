package br.com.unimotors.catalogo.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "especificacoes_veiculo")
public class EspecificacaoVeiculo {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    @Column
    private Integer ano;

    @Column(length = 20)
    private String combustivel;

    @Column(length = 20)
    private String cambio;

    @Column(length = 30)
    private String carroceria;

    @Column
    private Integer portas;

    public EspecificacaoVeiculo() {
        this.id = UUID.randomUUID();
    }

    public EspecificacaoVeiculo(
            Modelo modelo,
            Integer ano,
            String combustivel,
            String cambio,
            String carroceria,
            Integer portas
    ) {
        this();
        this.modelo = modelo;
        this.ano = ano;
        this.combustivel = combustivel;
        this.cambio = cambio;
        this.carroceria = carroceria;
        this.portas = portas;
    }

    public UUID getId() { return id; }
    public Modelo getModelo() { return modelo; }
    public void setModelo(Modelo modelo) { this.modelo = modelo; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    public String getCombustivel() { return combustivel; }
    public void setCombustivel(String combustivel) { this.combustivel = combustivel; }
    public String getCambio() { return cambio; }
    public void setCambio(String cambio) { this.cambio = cambio; }
    public String getCarroceria() { return carroceria; }
    public void setCarroceria(String carroceria) { this.carroceria = carroceria; }
    public Integer getPortas() { return portas; }
    public void setPortas(Integer portas) { this.portas = portas; }
}
