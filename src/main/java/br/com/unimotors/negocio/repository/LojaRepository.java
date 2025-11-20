package br.com.unimotors.negocio.repository;

import br.com.unimotors.negocio.model.Loja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LojaRepository extends JpaRepository<Loja, UUID> {
}

