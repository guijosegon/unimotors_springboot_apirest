package br.com.unimotors.catalogo.repository;

import br.com.unimotors.catalogo.model.Opcional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OpcionalRepository extends JpaRepository<Opcional, UUID> {

    boolean existsByNomeIgnoreCase(String nome);
}
