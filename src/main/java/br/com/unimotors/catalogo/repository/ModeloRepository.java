package br.com.unimotors.catalogo.repository;

import br.com.unimotors.catalogo.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModeloRepository extends JpaRepository<Modelo, UUID> {

    // Verifica existencia de modelo com mesmo nome (ignorando case), mesmo ano e mesma marca (marca.id)
    boolean existsByNomeIgnoreCaseAndAnoAndMarca_Id(String nome, Integer ano, UUID marcaId);

}
