package br.com.unimotors.negocio.repository;

import br.com.unimotors.negocio.model.Proposta;
import br.com.unimotors.negocio.model.StatusProposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PropostaRepository extends JpaRepository<Proposta, UUID> {

    List<Proposta> findByAnuncio_IdOrderByCriadoEmAsc(UUID anuncioId);

    List<Proposta> findByAnuncio_IdAndStatus(UUID anuncioId, StatusProposta status);
}
