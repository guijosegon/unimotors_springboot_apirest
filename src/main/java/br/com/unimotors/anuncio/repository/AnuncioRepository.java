package br.com.unimotors.anuncio.repository;

import br.com.unimotors.anuncio.model.AnuncioVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnuncioRepository extends JpaRepository<AnuncioVeiculo, UUID> {
}
