package br.com.unimotors.catalogo.repository;

import br.com.unimotors.catalogo.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ModeloRepository extends JpaRepository<Modelo, UUID> {

    List<Modelo> findByMarcaId(UUID marcaId);
}
