package br.com.unimotors.anuncio.repository;

import br.com.unimotors.anuncio.model.AnuncioOpcional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnuncioOpcionalRepository extends JpaRepository<AnuncioOpcional, UUID> {

    boolean existsByAnuncioIdAndOpcionalId(UUID anuncioId, UUID opcionalId);

    List<AnuncioOpcional> findByAnuncioId(UUID anuncioId);

}
