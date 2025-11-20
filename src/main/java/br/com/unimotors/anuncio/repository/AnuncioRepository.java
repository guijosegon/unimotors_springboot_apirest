package br.com.unimotors.anuncio.repository;

import br.com.unimotors.anuncio.model.AnuncioVeiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface AnuncioRepository extends JpaRepository<AnuncioVeiculo, UUID> {

    @Query("""
            select a from AnuncioVeiculo a
            where (:marca is null or lower(a.modelo.marca.nome) like lower(concat('%', :marca, '%')))
              and (:modelo is null or lower(a.modelo.nome) like lower(concat('%', :modelo, '%')))
              and (:precoMin is null or a.preco >= :precoMin)
              and (:cidade is null or lower(a.cidade) like lower(concat('%', :cidade, '%')))
            """)
    Page<AnuncioVeiculo> buscarPorFiltro(@Param("marca") String marca,
                                         @Param("modelo") String modelo,
                                         @Param("precoMin") BigDecimal precoMin,
                                         @Param("cidade") String cidade,
                                         Pageable pageable);
}
