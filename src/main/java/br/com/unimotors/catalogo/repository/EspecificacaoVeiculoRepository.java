package br.com.unimotors.catalogo.repository;

import br.com.unimotors.catalogo.model.EspecificacaoVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EspecificacaoVeiculoRepository extends JpaRepository<EspecificacaoVeiculo, UUID> {

    List<EspecificacaoVeiculo> findByModeloId(UUID modeloId);
}

