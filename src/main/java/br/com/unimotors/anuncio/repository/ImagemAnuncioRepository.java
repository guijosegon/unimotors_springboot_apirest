package br.com.unimotors.anuncio.repository;

import br.com.unimotors.anuncio.model.ImagemAnuncio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImagemAnuncioRepository extends JpaRepository<ImagemAnuncio, UUID> {
}

