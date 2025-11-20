package br.com.unimotors.negocio.repository;

import br.com.unimotors.negocio.model.Favorito;
import br.com.unimotors.negocio.model.FavoritoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FavoritoRepository extends JpaRepository<Favorito, FavoritoId> {

    @Query("select f from Favorito f join fetch f.anuncio where f.usuario.id = :usuarioId")
    List<Favorito> findByUsuarioId(@Param("usuarioId") UUID usuarioId);
}

