package br.com.unimotors.catalogo.repository;

import br.com.unimotors.catalogo.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MarcaRepository extends JpaRepository<Marca, UUID> {
}
