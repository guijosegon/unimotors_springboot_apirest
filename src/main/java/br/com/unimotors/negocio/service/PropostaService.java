package br.com.unimotors.negocio.service;

import br.com.unimotors.anuncio.model.AnuncioVeiculo;
import br.com.unimotors.anuncio.model.StatusAnuncio;
import br.com.unimotors.anuncio.repository.AnuncioRepository;
import br.com.unimotors.negocio.dto.PropostaCriarDTO;
import br.com.unimotors.negocio.dto.PropostaRespostaDTO;
import br.com.unimotors.negocio.model.Proposta;
import br.com.unimotors.negocio.model.StatusProposta;
import br.com.unimotors.negocio.repository.PropostaRepository;
import br.com.unimotors.usuario.model.Usuario;
import br.com.unimotors.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PropostaService {

    private static final Logger log = LoggerFactory.getLogger(PropostaService.class);

    private final PropostaRepository propostas;
    private final AnuncioRepository anuncios;
    private final UsuarioRepository usuarios;

    public PropostaService(PropostaRepository propostas,
                           AnuncioRepository anuncios,
                           UsuarioRepository usuarios) {
        this.propostas = propostas;
        this.anuncios = anuncios;
        this.usuarios = usuarios;
    }

    @Transactional
    public PropostaRespostaDTO criar(UUID anuncioId, String emailComprador, PropostaCriarDTO dto) {
        AnuncioVeiculo anuncio = anuncios.findById(anuncioId)
                .orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado"));
        Usuario comprador = usuarios.findByEmail(emailComprador)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (anuncio.getProprietario().getId().equals(comprador.getId())) {
            throw new IllegalArgumentException("Comprador não pode enviar proposta para o próprio anúncio");
        }

        if (dto.valor() == null || dto.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da proposta deve ser maior que zero");
        }

        Proposta proposta = new Proposta(anuncio, comprador, dto.valor(), dto.mensagem());
        Proposta salva = propostas.save(proposta);
        log.info("Proposta criada para anuncio {} por {}", anuncio.getId(), comprador.getEmail());
        return toRespostaDTO(salva);
    }

    @Transactional(readOnly = true)
    public List<PropostaRespostaDTO> listarPorAnuncio(UUID anuncioId, String emailUsuario, boolean isAdmin) {
        AnuncioVeiculo anuncio = anuncios.findById(anuncioId)
                .orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado"));
        garantirProprietarioOuAdmin(emailUsuario, isAdmin, anuncio);
        return propostas.findByAnuncio_IdOrderByCriadoEmAsc(anuncioId)
                .stream()
                .map(this::toRespostaDTO)
                .toList();
    }

    @Transactional
    public PropostaRespostaDTO aceitar(UUID propostaId, String emailUsuario, boolean isAdmin) {
        Proposta proposta = propostas.findById(propostaId)
                .orElseThrow(() -> new EntityNotFoundException("Proposta não encontrada"));
        AnuncioVeiculo anuncio = proposta.getAnuncio();
        garantirProprietarioOuAdmin(emailUsuario, isAdmin, anuncio);

        proposta.setStatus(StatusProposta.ACEITA);
        anuncio.setStatus(StatusAnuncio.RESERVADO);

        // recusar automaticamente outras propostas pendentes
        List<Proposta> pendentes = propostas.findByAnuncio_IdAndStatus(anuncio.getId(), StatusProposta.PENDENTE);
        for (Proposta p : pendentes) {
            if (!p.getId().equals(proposta.getId())) {
                p.setStatus(StatusProposta.RECUSADA);
            }
        }

        log.info("Proposta {} aceita para anuncio {}", propostaId, anuncio.getId());
        return toRespostaDTO(proposta);
    }

    @Transactional
    public PropostaRespostaDTO recusar(UUID propostaId, String emailUsuario, boolean isAdmin) {
        Proposta proposta = propostas.findById(propostaId)
                .orElseThrow(() -> new EntityNotFoundException("Proposta não encontrada"));
        AnuncioVeiculo anuncio = proposta.getAnuncio();
        garantirProprietarioOuAdmin(emailUsuario, isAdmin, anuncio);

        proposta.setStatus(StatusProposta.RECUSADA);
        log.info("Proposta {} recusada para anuncio {}", propostaId, anuncio.getId());
        return toRespostaDTO(proposta);
    }

    private void garantirProprietarioOuAdmin(String emailUsuario, boolean isAdmin, AnuncioVeiculo anuncio) {
        if (!isAdmin && !anuncio.getProprietario().getEmail().equalsIgnoreCase(emailUsuario)) {
            throw new IllegalArgumentException("Usuário não é proprietário do anúncio");
        }
    }

    private PropostaRespostaDTO toRespostaDTO(Proposta p) {
        return new PropostaRespostaDTO(
                p.getId(),
                p.getAnuncio().getId(),
                p.getComprador().getId(),
                p.getValor(),
                p.getStatus().name(),
                p.getCriadoEm()
        );
    }
}
